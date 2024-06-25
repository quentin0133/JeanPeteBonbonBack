package fr.dawan.jeankasskouille.core.tools;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class DateUtils {
    public static Optional<LocalDateTime> getMostRecentDate(List<LocalDateTime> list) {
        return list.stream().min(LocalDateTime::compareTo);
    }

    public static Optional<LocalTime> getMostLateTime(List<LocalTime> list) {
        return list.stream().max(LocalTime::compareTo);
    }
}
