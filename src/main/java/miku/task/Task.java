package miku.task;

import java.io.Serializable;

public abstract class Task implements Serializable {
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
    
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }
}
