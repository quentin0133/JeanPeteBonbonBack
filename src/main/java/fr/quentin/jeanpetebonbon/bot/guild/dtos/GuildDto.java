package fr.quentin.jeanpetebonbon.bot.guild.dtos;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuildDto {
    private long id;
    private String label;
    private long idTextChannel;
    private boolean trolling;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        GuildDto guildDto = (GuildDto) object;
        return id == guildDto.id && idTextChannel == guildDto.idTextChannel && trolling == guildDto.trolling && Objects.equals(label, guildDto.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, idTextChannel, trolling);
    }

    @Override
    public String toString() {
        return "GuildDto{" +
            "id=" + id +
            ", label='" + label + '\'' +
            ", idTextChannel=" + idTextChannel +
            ", trolling=" + trolling +
            '}';
    }
}
