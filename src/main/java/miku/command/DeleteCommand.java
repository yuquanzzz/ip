package miku.command;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.TextUi;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructs a DeleteCommand with the specified task index.
     *
     * @param index The index of the task to be deleted (0-based).
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, TextUi ui, Storage storage) throws MikuException {
        tasks.deleteTask(index);
        storage.saveTaskList(tasks);
        ui.showTaskDeleted(tasks.getTask(index), tasks.size());
    }
}
