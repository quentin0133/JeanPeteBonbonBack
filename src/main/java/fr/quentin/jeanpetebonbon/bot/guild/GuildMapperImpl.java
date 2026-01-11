package fr.quentin.jeanpetebonbon.bot.guild;

import fr.quentin.jeanpetebonbon.bot.guild.dtos.GuildDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GuildMapperImpl implements GuildMapper {

    @Override
    public Guild toEntity(GuildDto dto) {
        if ( dto == null ) {
            return null;
        }

        Guild guild = new Guild();

        guild.setId( dto.getId() );
        guild.setLabel( dto.getLabel() );
        guild.setIdTextChannel( dto.getIdTextChannel() );
        guild.setTrolling( dto.isTrolling() );

        return guild;
    }

    @Override
    public GuildDto toDto(Guild entity) {
        if ( entity == null ) {
            return null;
        }

        GuildDto guildDto = new GuildDto();

        guildDto.setId( entity.getId() );
        guildDto.setLabel( entity.getLabel() );
        guildDto.setIdTextChannel( entity.getIdTextChannel() );
        guildDto.setTrolling( entity.isTrolling() );

        return guildDto;
    }
}
