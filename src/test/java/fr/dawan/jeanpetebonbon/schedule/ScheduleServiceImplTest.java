package fr.dawan.jeanpetebonbon.schedule;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import fr.dawan.jeanpetebonbon.JeanPetebonbonApplication;
import fr.dawan.jeanpetebonbon.bot.guild.*;
import fr.dawan.jeanpetebonbon.bot.guild.dtos.GuildDto;
import fr.dawan.jeanpetebonbon.core.generic.GenericServiceImpl;
import fr.dawan.jeanpetebonbon.schedule.dtos.ScheduleDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {
  private ScheduleService service;
  @Mock private ScheduleRepository repository;
  @Mock private ScheduleMapper mapper;

  @Mock private GuildService guildService;

  private ScheduleDto dto;
  private Schedule entity;
  private GuildDto guildDto;

  @BeforeEach
  void init() {
    service = new ScheduleServiceImpl(repository, mapper, guildService);
  }

  @Test
  void save() {
    guildDto = GuildDto.builder().id(5L).build();

    entity =
        Schedule.builder()
            .id(2L)
            .version(0)
            .dates(Set.of(LocalDate.of(2010, 11, 5)))
            .times(Set.of(LocalTime.of(12, 14)))
            .guild(Guild.builder().id(guildDto.getId()).build())
            .build();

    dto =
        ScheduleDto.builder()
            .id(0)
            .version(0)
            .dates(Set.of(LocalDate.of(2010, 11, 5)))
            .times(Set.of(LocalTime.of(12, 14)))
            .guildId(guildDto.getId())
            .build();

    try (MockedStatic<JeanPetebonbonApplication> ignored =
        Mockito.mockStatic(JeanPetebonbonApplication.class)) {
      JDA mockJda = mock(JDA.class);
      when(JeanPetebonbonApplication.getJda()).thenReturn(mockJda);

      TextChannel mockTextChannel = mock(TextChannel.class);
      when(mockJda.getTextChannelById(anyLong())).thenReturn(mockTextChannel);

      ScheduleService spyService = spy(service);
      doNothing().when(spyService).startSchedules(anyLong(), any(TextChannel.class));

      when(repository.saveAndFlush(isA(Schedule.class))).thenReturn(entity);
      when(guildService.findById(anyLong())).thenReturn(guildDto);
      when(mapper.toDto(isA(Schedule.class)))
          .thenAnswer(invocation -> toDto(invocation.getArgument(0)));
      when(mapper.toEntity(isA(ScheduleDto.class)))
          .thenAnswer(invocation -> toEntity(invocation.getArgument(0)));

      ScheduleDto scheduleDto = spyService.save(dto);

      // verify(repository).saveAndFlush(entity);
      verify(mockJda).getTextChannelById(anyLong());
      verify(spyService).startSchedules(anyLong(), any(TextChannel.class));

      assertEquals(2L, scheduleDto.getId());
      assertEquals(0, scheduleDto.getVersion());
      assertEquals(dto.getGuildId(), scheduleDto.getGuildId());
      assertEquals(dto.getTimes(), scheduleDto.getTimes());
      assertEquals(dto.getDates(), scheduleDto.getDates());
    }
  }

  @Test
  void startSchedules() {}

  @Test
  void initSchedule() {}

  @Test
  void sendScheduleMessage() {}

  @Test
  void clearSchedule() {}

  // region Mapper
  public ScheduleDto toDto(Schedule entity) {
    if (entity == null) {
      return null;
    } else {
      ScheduleDto.ScheduleDtoBuilder<?, ?> scheduleDto = ScheduleDto.builder();
      scheduleDto.guildId(entity.getGuild().getId());
      scheduleDto.id(entity.getId());
      scheduleDto.version(entity.getVersion());
      scheduleDto.message(entity.getMessage());

      if (entity.getDates() != null) scheduleDto.dates(new LinkedHashSet<>(entity.getDates()));
      if (entity.getTimes() != null) scheduleDto.times(new LinkedHashSet<>(entity.getTimes()));

      return scheduleDto.build();
    }
  }

  public Schedule toEntity(ScheduleDto dto) {
    if (dto == null) {
      return null;
    } else {
      Schedule.ScheduleBuilder<?, ?> schedule = Schedule.builder();
      schedule.guild(this.scheduleDtoToGuild(dto));
      schedule.id(dto.getId());
      schedule.version(dto.getVersion());
      schedule.message(dto.getMessage());
      Set<LocalDate> set = dto.getDates();
      if (set != null) {
        schedule.dates(new LinkedHashSet<>(set));
      }

      Set<LocalTime> set1 = dto.getTimes();
      if (set1 != null) {
        schedule.times(new LinkedHashSet<>(set1));
      }

      return schedule.build();
    }
  }

  protected Guild scheduleDtoToGuild(ScheduleDto scheduleDto) {
    if (scheduleDto.getGuildId() != null)
      return Guild.builder().id(scheduleDto.getGuildId()).build();
    return new Guild();
  }
  // endregion
}
