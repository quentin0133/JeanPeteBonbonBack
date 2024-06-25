package fr.dawan.jeankasskouille.message.trigger;

import fr.dawan.jeankasskouille.message.response.MessageResponseTroll;
import fr.dawan.jeankasskouille.core.generic.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageTriggerTroll extends BaseEntity {
    private String messageTrigger;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<MessageResponseTroll> messagesResponseTroll;
}
