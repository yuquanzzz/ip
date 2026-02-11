package miku.command;

import java.util.ArrayList;
import java.util.List;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.Task;
import miku.task.TaskList;
import miku.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final List<Integer> indexes;

    /**
     * Constructs a MarkCommand with the specified task indexes.
     *
     * @param indexes The indexes of the tasks to be marked as done (0-based).
     */
    public MarkCommand(List<Integer> indexes) {
        this.indexes = List.copyOf(indexes);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException {
        assert tasks != null : "tasks must be non-null";
        assert ui != null : "ui must be non-null";
        assert storage != null : "storage must be non-null";

        List<Task> markedTasks = new ArrayList<>();
        // iterate through indexes and mark each task
        for (int index : indexes) {
            Task task = tasks.getTask(index);
            tasks.markTask(index);
            markedTasks.add(task);
        }
        ui.showTasksMarked(markedTasks);
        storage.saveTaskList(tasks);
    }
}
