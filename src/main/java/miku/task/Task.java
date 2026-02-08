package miku.task;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an abstract task with a description and completion status.
 * This is the base class for all task types (Todo, Deadline, Event).
 * Implements Serializable to allow tasks to be saved to and loaded from storage.
 */
public abstract class Task implements Serializable {
    public static final String DATETIME_FORMAT = "MMM dd yyyy HH:mm";
    public static final String MARKED_SYMBOL = "[X] ";
    public static final String UNMARKED_SYMBOL = "[ ] ";

    @Serial
    private static final long serialVersionUID = 1L;

    private final String description;
    private boolean isDone;

    /**
     * Constructs a new Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return (isDone ? MARKED_SYMBOL : UNMARKED_SYMBOL) + description;
    }
}
