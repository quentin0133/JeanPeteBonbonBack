package fr.dawan.jeankasskouille.schedule;

import fr.dawan.jeankasskouille.JeanKasskouilleApplication;
import fr.dawan.jeankasskouille.bot.guild.GuildRepository;
import fr.dawan.jeankasskouille.core.exception.NoServerFound;
import fr.dawan.jeankasskouille.core.generic.GenericServiceImpl;
import fr.dawan.jeankasskouille.schedule.dtos.ScheduleDto;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static fr.dawan.jeankasskouille.core.tools.DateUtils.getMostLateTime;
import static fr.dawan.jeankasskouille.core.tools.DateUtils.getMostRecentDate;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

@Service
@Slf4j
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule, ScheduleRepository, ScheduleDto, ScheduleMapper> implements ScheduleService {
    private final Map<Long, ScheduledExecutorService> scheduledEvent = new HashMap<>();
    private final Map<Long, ScheduledFuture<?>> schedulesEventHandler = new HashMap<>();

    private final GuildRepository guildRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository repository, ScheduleMapper mapper, GuildRepository guildRepository) {
        super(repository, mapper);
        this.guildRepository = guildRepository;
    }

    @Override
    public ScheduleDto save(ScheduleDto dto) throws NoServerFound {
        Optional<fr.dawan.jeankasskouille.bot.guild.Guild> guild = guildRepository.findById(dto.getGuildId());
        if (guild.isEmpty()) throw new NoServerFound();
        ScheduleDto scheduleDto = super.save(dto);
        TextChannel textChannel = JeanKasskouilleApplication.getJda().getTextChannelById(guild.get().getIdTextChannel());
        startSchedules(scheduleDto.getGuildId(), textChannel);
        return scheduleDto;
    }

    @Override
    public void startSchedules(long idGuild, TextChannel textChannel) {
        ScheduledFuture<?> scheduledFuture = schedulesEventHandler.get(idGuild);
        if (scheduledFuture != null)
            scheduledFuture.cancel(true);
        Optional<Schedule> scheduleOptional = getLastSchedule(repository.findByGuildId(idGuild)
            .stream()
            .filter(this::removeUnusedSchedule)
            .toList());
        if (scheduleOptional.isEmpty()) return;
        Schedule schedule = scheduleOptional.get();
        LocalDateTime localDateTimeNext = getMostRecentDate(schedule.getLocalDateTime().stream()
            .filter(localDateTime -> localDateTime.isAfter(LocalDateTime.now())).toList())
            .orElseThrow(IllegalArgumentException::new);

        schedulesEventHandler.put(idGuild,
            scheduledEvent.get(idGuild).schedule(
                () -> sendMessage(idGuild, textChannel, localDateTimeNext),
                Duration.between(LocalDateTime.now(), localDateTimeNext).getSeconds(),
                TimeUnit.SECONDS
            )
        );
    }

    private void sendMessage(long idGuild, TextChannel textChannel,
                             LocalDateTime localDateTimeNext) {
        repository.findByGuildId(idGuild).stream()
            .filter(otherShedule -> otherShedule.getLocalDateTime().stream()
                .anyMatch(localDateTime -> Math.abs(SECONDS.between(localDateTime, localDateTimeNext)) < 1))
            .forEach(sameSchedule -> sendScheduleMessage(sameSchedule, textChannel));

        schedulesEventHandler.put(idGuild, null);

        getLastSchedule(repository.findByGuildId(idGuild))
            .ifPresent(nextSchedule -> startSchedules(idGuild, textChannel));
    }

    @Override
    public void initSchedule(Guild guild, TextChannel textChannel) {
        scheduledEvent.put(guild.getIdLong(), Executors.newScheduledThreadPool(1));
        schedulesEventHandler.put(guild.getIdLong(), null);
        startSchedules(guild.getIdLong(), textChannel);
    }

    @Override
    public void sendScheduleMessage(Schedule schedule, TextChannel textChannel) {
        textChannel.sendMessage(schedule.getMessage()).queue();
        removeUnusedSchedule(schedule);
    }

    @Override
    public void clearSchedule(long idGuild) {
        scheduledEvent.get(idGuild).shutdown();
        ScheduledFuture<?> scheduledHandler = schedulesEventHandler.get(idGuild);
        if (scheduledHandler != null)
            scheduledHandler.cancel(true);
    }

    private Optional<Schedule> getLastSchedule(List<Schedule> schedules) {
        if (schedules.isEmpty()) return Optional.empty();
        return schedules.stream().min(Comparator.comparing(other ->
            getMostRecentDate(other.getLocalDateTime()).orElse(LocalDateTime.MAX)));
    }

    private boolean removeUnusedSchedule(Schedule schedule) {
        // Delete when schedules doesn't have more schedules date valid
        // Update the schedules to remove the date that are in the past
        LocalTime mostLateTime = getMostLateTime(schedule.getTimes()).orElse(LocalTime.MAX);
        schedule.getDates().removeAll(
            schedule.getDates().stream().filter(date -> {
                    if (date.isBefore(LocalDate.now()))
                        return false;
                    LocalTime currentTime = LocalTime.now().plusSeconds(5L);
                    System.out.println("mostLateTime : " + mostLateTime);
                    System.out.println("LocalTime.now() : " + currentTime);
                    return mostLateTime.isBefore(currentTime);
                })
                .toList()
        );

        if (schedule.getDates().isEmpty()) {
            repository.deleteById(schedule.getId());
            return false;
        }

        return true;
    }
}
