package fr.dawan.jeanpetebonbon.schedule;

import fr.dawan.jeanpetebonbon.schedule.dtos.ScheduleDto;
import fr.dawan.jeanpetebonbon.core.generic.GenericService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public interface ScheduleService extends GenericService<Schedule, ScheduleDto> {
  void initSchedule(Guild guild);

  void clearSchedule(long idGuild);

  void sendScheduleMessage(Schedule schedule, TextChannel textChannel);

  void startSchedules(long idGuild, TextChannel textChannel);
}
