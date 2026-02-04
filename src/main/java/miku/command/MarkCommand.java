package miku.command;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int index;

    /**
     * Constructs a MarkCommand with the specified task index.
     *
     * @param index The index of the task to be marked as done (0-based).
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException {
        tasks.markTask(index);
        storage.saveTaskList(tasks);
        ui.showTaskMarked(tasks.getTask(index));
    }
}
