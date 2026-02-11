package miku.command;

import java.util.ArrayList;
import java.util.List;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.Task;
import miku.task.TaskList;
import miku.ui.Ui;

/**
 * Represents a command to unmark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final List<Integer> indexes;

    /**
     * Constructs an UnmarkCommand with the specified task indexes.
     *
     * @param indexes The indexes of the tasks to be unmarked (0-based).
     */
    public UnmarkCommand(List<Integer> indexes) {
        this.indexes = List.copyOf(indexes);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException {
        assert tasks != null : "tasks must be non-null";
        assert ui != null : "ui must be non-null";
        assert storage != null : "storage must be non-null";

        List<Task> unmarkedTasks = new ArrayList<>();
        // iterate through indexes and unmark each task
        for (int index : indexes) {
            Task task = tasks.getTask(index);
            tasks.unmarkTask(index);
            unmarkedTasks.add(task);
        }
        ui.showTasksUnmarked(unmarkedTasks);
        storage.saveTaskList(tasks);
    }
}
