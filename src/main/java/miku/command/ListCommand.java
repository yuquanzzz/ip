package miku.command;

import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.TextUi;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, TextUi ui, Storage storage) {
        ui.showTaskList(tasks.getTasks());
    }
}
