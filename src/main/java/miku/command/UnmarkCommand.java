package miku.command;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.Ui;

/**
 * Represents a command to unmark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Constructs an UnmarkCommand with the specified task index.
     *
     * @param index The index of the task to be unmarked (0-based).
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException {
        assert tasks != null : "tasks must be non-null";
        assert ui != null : "ui must be non-null";
        assert storage != null : "storage must be non-null";
        tasks.unmarkTask(index);
        storage.saveTaskList(tasks);
        ui.showTaskUnmarked(tasks.getTask(index));
    }
}
