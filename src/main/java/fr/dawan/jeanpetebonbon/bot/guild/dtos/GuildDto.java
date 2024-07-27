package fr.dawan.jeanpetebonbon.bot.guild.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class GuildDto {
    private long id;
    private String label;
    private long idTextChannel;
    private boolean trolling;
}
