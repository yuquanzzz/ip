package miku.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import miku.exception.MikuException;

public class TaskList implements Serializable {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) throws MikuException {
        if (index < 0 || index >= tasks.size()) {
            throw new MikuException("Task number " + (index + 1) + " does not exist!");
        }
        return tasks.get(index);
    }

    public void markTask(int index) throws MikuException {
        if (index < 0 || index >= tasks.size()) {
            throw new MikuException("Task number " + (index + 1) + " does not exist!");
        }
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) throws MikuException {
        if (index < 0 || index >= tasks.size()) {
            throw new MikuException("Task number " + (index + 1) + " does not exist!");
        }
        tasks.get(index).markAsUndone();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Deletes a task from the task list at the specified index.
     *
     * @param index The index of the task to delete (0-based).
     */
    public void deleteTask(int index) {
        tasks.remove(index);
    }
}
