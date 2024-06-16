package fr.dawan.jeankasskouille.business.component.message.response;

import fr.dawan.jeankasskouille.generic.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseTroll extends BaseEntity {
    private String messageResponse;
}
