package fr.dawan.jeankasskouille.business.component.message.trigger;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageTriggerTrollRepository extends JpaRepository<MessageTriggerTroll,Long> {
    Optional<MessageTriggerTroll> findByMessageTrigger(String messageTrigger);
}
