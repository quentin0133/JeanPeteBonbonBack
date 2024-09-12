package fr.dawan.jeanpetebonbon.bot.guild.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuildDto {
    private long id;
    private String label;
    private long idTextChannel;
    private boolean trolling;
}
