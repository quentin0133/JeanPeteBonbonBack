package fr.dawan.jeankasskouille.guildregistration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuildRegistrationDto {
    private long id;
    private int version;
    private long idTextChannel;
}
