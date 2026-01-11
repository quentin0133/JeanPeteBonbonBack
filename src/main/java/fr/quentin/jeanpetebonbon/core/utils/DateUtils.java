package fr.quentin.jeanpetebonbon.core.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class DateUtils {
    private DateUtils() {
    }

    public static Optional<LocalDateTime> getMostRecentDateTime(Collection<LocalDateTime> list) {
        return list.stream().min(LocalDateTime::compareTo);
    }

    public static Optional<LocalTime> getMostLateTime(Collection<LocalTime> list) {
        return list.stream().max(LocalTime::compareTo);
    }

    public static Stream<LocalTime> getTimesBefore(Collection<LocalTime> times, LocalTime timeCompared) {
        return times.stream().filter(time -> time.isAfter(timeCompared));
    }
}
