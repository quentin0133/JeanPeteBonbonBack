package fr.dawan.jeankasskouille.message.clash;

import fr.dawan.jeankasskouille.message.response.MessageResponseTrollDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageClashTrollDto {
    private long id;
    private int version;
    private String messageClash;
}
