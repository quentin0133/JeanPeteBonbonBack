package fr.quentin.jeanpetebonbon.schedule.dtos;

import java.time.LocalDateTime;

public record ScheduledEventDto(
    long scheduleId,
    long guildId,
    LocalDateTime executeAt
) {}