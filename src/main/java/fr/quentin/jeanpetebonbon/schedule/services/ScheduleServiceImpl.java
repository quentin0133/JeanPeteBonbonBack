package fr.quentin.jeanpetebonbon.schedule.services;

import fr.quentin.jeanpetebonbon.bot.guild.Guild;
import fr.quentin.jeanpetebonbon.bot.guild.GuildService;
import fr.quentin.jeanpetebonbon.bot.guild.dtos.GuildDto;
import fr.quentin.jeanpetebonbon.core.exception.NoServerFound;
import fr.quentin.jeanpetebonbon.core.generic.CrudServiceImpl;
import fr.quentin.jeanpetebonbon.schedule.Schedule;
import fr.quentin.jeanpetebonbon.schedule.ScheduleMapper;
import fr.quentin.jeanpetebonbon.schedule.ScheduleRepository;
import fr.quentin.jeanpetebonbon.schedule.dtos.ScheduleLightDto;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static fr.quentin.jeanpetebonbon.core.utils.DateUtils.*;
import static java.time.temporal.ChronoUnit.SECONDS;

@Service
@Slf4j
public class ScheduleServiceImpl
    extends CrudServiceImpl<Schedule, ScheduleRepository, ScheduleLightDto, ScheduleMapper>
    implements ScheduleService {
  private final Map<Long, ScheduledExecutorService> scheduledEvent;
  private final Map<Long, ScheduledFuture<?>> schedulesEventHandler;
  private final GuildService guildService;
  private final JDA jda;

  @Autowired
  public ScheduleServiceImpl(
      ScheduleRepository repository,
      ScheduleMapper mapper,
      GuildService guildService, JDA jda) {
    super(repository, mapper, Schedule.class);
    scheduledEvent = new HashMap<>();
    schedulesEventHandler = new HashMap<>();
    this.guildService = guildService;
    this.jda = jda;
  }

  @Override
  public ScheduleLightDto save(ScheduleLightDto dto) throws NoServerFound {
    GuildDto guildDto = guildService.findById(dto.getGuildId());
    ScheduleLightDto scheduleLightDto = super.save(dto);
    TextChannel textChannel = jda.getTextChannelById(guildDto.getIdTextChannel());
    startSchedules(scheduleLightDto.getGuildId(), textChannel);
    return scheduleLightDto;
  }

  @Override
  public void startSchedules(long idGuild, TextChannel textChannel) {
    Optional<Schedule> lastScheduleOptional =
        getLastSchedule(
            repository.findByGuildId(idGuild).stream()
                .filter(this::refreshSchedule)
                .map(schedule -> {
                      schedule.setTimes(
                          getTimesBefore(schedule.getTimes(), LocalTime.now().plusSeconds(5))
                              .collect(Collectors.toSet()));
                      return schedule;
                    })
                .toList());
    if (lastScheduleOptional.isEmpty()) return;
    ScheduledFuture<?> scheduledFuture = schedulesEventHandler.get(idGuild);
    if (scheduledFuture != null) scheduledFuture.cancel(true);
    Optional<LocalDateTime> localDateTimeNext =
        getMostRecentDateTime(
            lastScheduleOptional.get().getLocalDateTime().stream()
                .filter(localDateTime -> localDateTime.isAfter(LocalDateTime.now().plusSeconds(5)))
                .toList());
    if (localDateTimeNext.isEmpty()) return;
    schedulesEventHandler.put(
        idGuild,
        scheduledEvent
            .get(idGuild)
            .schedule(
                () -> sendMessage(idGuild, textChannel, localDateTimeNext.get()),
                Duration.between(LocalDateTime.now(), localDateTimeNext.get()).getSeconds(),
                TimeUnit.SECONDS));
  }

  private void sendMessage(long idGuild, TextChannel textChannel, LocalDateTime localDateTimeNext) {
    repository.findByGuildId(idGuild).stream()
        .filter(
            otherSchedule ->
                otherSchedule.getLocalDateTime().stream()
                    .anyMatch(
                        localDateTime ->
                            Math.abs(SECONDS.between(localDateTime, localDateTimeNext)) < 1))
        .forEach(sameSchedule -> sendScheduleMessage(sameSchedule, textChannel));
    schedulesEventHandler.put(idGuild, null);
    getLastSchedule(repository.findByGuildId(idGuild))
        .ifPresent(nextSchedule -> startSchedules(idGuild, textChannel));
  }

  @Override
  public void initSchedule(net.dv8tion.jda.api.entities.Guild guild) {
    if (!guildService.existsById(guild.getIdLong()))
      guildService.saveEntity(new Guild(guild.getIdLong(), guild.getName()));
    scheduledEvent.put(guild.getIdLong(), Executors.newScheduledThreadPool(1));
    schedulesEventHandler.put(guild.getIdLong(), null);
  }

  @Override
  public void sendScheduleMessage(Schedule schedule, TextChannel textChannel) {
    textChannel.sendMessage(schedule.getMessage()).queue();
    refreshSchedule(schedule);
  }

  @Override
  public void clearSchedule(long idGuild) {
    scheduledEvent.get(idGuild).shutdown();
    ScheduledFuture<?> scheduledHandler = schedulesEventHandler.get(idGuild);
    if (scheduledHandler != null) scheduledHandler.cancel(true);
  }

  private Optional<Schedule> getLastSchedule(List<Schedule> schedules) {
    if (schedules.isEmpty()) return Optional.empty();
    return schedules.stream()
        .min(
            Comparator.comparing(
                other ->
                    getMostRecentDateTime(other.getLocalDateTime()).orElse(LocalDateTime.MAX)));
  }

  private boolean refreshSchedule(Schedule schedule) {
    // Delete when schedules doesn't have more schedules date valid
    // Update the schedules to remove the date that are in the past
    LocalTime mostLateTime = getMostLateTime(schedule.getTimes()).orElse(LocalTime.MAX);
    schedule.getDates().stream()
        .filter(
            date -> {
              if (date.isAfter(LocalDate.now())) return false;
              return mostLateTime.isBefore(LocalTime.now().plusSeconds(5L));
            })
        .toList()
        .forEach(schedule.getDates()::remove);

    repository.saveAndFlush(schedule);

    if (schedule.getDates().isEmpty()) {
      repository.deleteById(schedule.getId());
      return false;
    }

    return true;
  }
}
