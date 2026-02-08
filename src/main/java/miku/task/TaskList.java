package miku.task;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import miku.exception.MikuException;

/**
 * Represents a list of tasks.
 * Provides operations to add, retrieve, mark, unmark, delete, and find tasks.
 * Implements Serializable to allow the task list to be saved to and loaded from storage.
 */
public class TaskList implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks); // read-only access for outside methods
    }

    public int size() {
        return tasks.size();
    }

    private void validateTaskIndex(int index) throws MikuException {
        if (index < 0 || index >= tasks.size()) {
            throw new MikuException("Task number " + (index + 1) + " does not exist!");
        }
    }

    /**
     * Marks a task as done at the specified index.
     *
     * @param index The index of the task to mark as done (0-based).
     * @throws MikuException If the index is out of bounds.
     */
    public void markTask(int index) throws MikuException {
        validateTaskIndex(index);
        tasks.get(index).markAsDone();
    }

    /**
     * Marks a task as not done at the specified index.
     *
     * @param index The index of the task to mark as not done (0-based).
     * @throws MikuException If the index is out of bounds.
     */
    public void unmarkTask(int index) throws MikuException {
        validateTaskIndex(index);
        tasks.get(index).markAsUndone();
    }

    /**
     * Deletes a task from the task list at the specified index.
     *
     * @param index The index of the task to delete (0-based).
     * @throws MikuException If the index is out of bounds.
     */
    public void deleteTask(int index) throws MikuException {
        validateTaskIndex(index);
        tasks.remove(index);
    }

    /**
     * Finds tasks that contain the specified keyword in their description.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return A list of tasks containing the keyword.
     */
    public List<Task> findTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}
