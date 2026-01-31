package miku.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs during a specific time period.
 */
public class Event extends Task {
    private final LocalDateTime fromTime;
    private final LocalDateTime toTime;

    /**
     * Constructs an Event task with the specified description, start time, and end time.
     *
     * @param description The description of the event task.
     * @param fromTime The start date and time of the event.
     * @param toTime The end date and time of the event.
     */
    public Event(String description, LocalDateTime fromTime, LocalDateTime toTime) {
        super(description);
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return "[E]" + super.toString()
                + " (from: " + fromTime.format(outputFormat)
                + " to: " + toTime.format(outputFormat) + ")";
    }
}
