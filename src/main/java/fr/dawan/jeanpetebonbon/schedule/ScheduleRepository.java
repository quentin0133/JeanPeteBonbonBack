package fr.dawan.jeanpetebonbon.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByGuildId(Long id);

    Optional<Schedule> findByMessage(String message);
}
