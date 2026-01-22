abstract class Task {
    private final String description;
    private boolean isDone;

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

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }
}

class TodoTask extends Task {
    TodoTask(String description) {
        super(description);
    }
}

class DeadlineTask extends Task {
    private final String deadline;

    DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline + ")";
    }
}

class EventTask extends Task {
    private final String eventTime;

    EventTask(String description, String eventTime) {
        super(description);
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return super.toString() + " (at: " + eventTime + ")";
    }
}