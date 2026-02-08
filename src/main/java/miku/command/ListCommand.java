package miku.command;

import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.Ui;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "tasks must be non-null";
        assert ui != null : "ui must be non-null";
        assert storage != null : "storage must be non-null";
        ui.showTaskList(tasks.getTasks());
    }
}
