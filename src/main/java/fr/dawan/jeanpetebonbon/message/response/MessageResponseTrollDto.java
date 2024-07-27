package fr.dawan.jeanpetebonbon.message.response;

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
