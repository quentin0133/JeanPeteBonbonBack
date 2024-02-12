package fr.dawan.jeankasskouille.message.clash;

import fr.dawan.jeankasskouille.generic.BaseEntity;
import jakarta.persistence.Column;
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
public class MessageClashTroll extends BaseEntity {
    @Column(length = 524)
    private String messageClash;
}
