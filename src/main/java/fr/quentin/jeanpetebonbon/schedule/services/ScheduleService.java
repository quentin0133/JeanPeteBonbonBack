package fr.quentin.jeanpetebonbon.schedule.services;

import fr.quentin.jeanpetebonbon.schedule.Schedule;
import fr.quentin.jeanpetebonbon.schedule.dtos.ScheduleLightDto;
import fr.quentin.jeanpetebonbon.core.generic.GenericService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public interface ScheduleService extends GenericService<Schedule, ScheduleLightDto> {
  void initSchedule(Guild guild);

  void clearSchedule(long idGuild);

  void sendScheduleMessage(Schedule schedule, TextChannel textChannel);

  void startSchedules(long idGuild, TextChannel textChannel);
}
