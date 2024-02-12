package fr.dawan.jeankasskouille.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseTrollDto {
    private long id;
    private int version;
    private String messageResponse;
}