package miku.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.Task;
import miku.task.TaskList;
import miku.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final List<Integer> indexes;

    /**
     * Constructs a DeleteCommand with the specified task indexes.
     *
     * @param indexes The indexes of the tasks to be deleted (0-based).
     */
    public DeleteCommand(List<Integer> indexes) {
        indexes.sort(Comparator.reverseOrder()); // delete in descending order to avoid index shifting
        this.indexes = List.copyOf(indexes);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException {
        assert tasks != null : "tasks must be non-null";
        assert ui != null : "ui must be non-null";
        assert storage != null : "storage must be non-null";

        List<Task> deletedTasks = new ArrayList<>();
        // iterate through indexes and delete each task
        for (int index : this.indexes) {
            Task task = tasks.getTask(index);
            tasks.deleteTask(index);
            deletedTasks.add(task);
        }
        ui.showTasksDeleted(deletedTasks, tasks.size());
        storage.saveTaskList(tasks);
    }
}
