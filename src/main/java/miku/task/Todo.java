package miku.task;

/**
 * Represents a todo task without any date/time attached to it.
 */
public class Todo extends Task {

    public static final String TODO_SYMBOL = "[T]";

    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return TODO_SYMBOL + super.toString();
    }
}
