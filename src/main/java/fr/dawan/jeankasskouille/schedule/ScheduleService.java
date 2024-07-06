package fr.dawan.jeankasskouille.schedule;

import fr.dawan.jeankasskouille.schedule.dtos.ScheduleDto;
import fr.dawan.jeankasskouille.core.generic.GenericService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;


public interface ScheduleService extends GenericService<ScheduleDto> {
  void initSchedule(Guild guild, TextChannel textChannel);

  void clearSchedule(long idGuild);

  void sendScheduleMessage(Schedule schedule, TextChannel textChannel);

  void startSchedules(long idGuild, TextChannel textChannel);
}
