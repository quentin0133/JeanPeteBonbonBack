package fr.dawan.jeankasskouille.business.component.message.clash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageClashTrollDto {
    private long id;
    private int version;
    private String messageClash;
}