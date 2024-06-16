package fr.dawan.jeankasskouille.business.component.schedule;

import fr.dawan.jeankasskouille.business.component.bot.guild.GuildMapper;
import fr.dawan.jeankasskouille.business.component.schedule.dtos.ScheduleDto;
import fr.dawan.jeankasskouille.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = GuildMapper.class)
public interface ScheduleMapper extends GenericMapper<Schedule, ScheduleDto> {
    @Override
    @Mapping(source = "guild.id", target = "guildId")
    ScheduleDto toDto(Schedule entity);

    @Override
    @Mapping(source = "guildId", target = "guild.id")
    Schedule toEntity(ScheduleDto dto);
}
