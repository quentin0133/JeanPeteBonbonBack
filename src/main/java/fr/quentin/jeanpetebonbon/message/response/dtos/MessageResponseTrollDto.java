package fr.quentin.jeanpetebonbon.message.response.dtos;

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
