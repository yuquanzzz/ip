package miku.command;

import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.Ui;

/**
 * Represents a command to exit the application.
 * Saves the task list and displays a goodbye message.
 */
public class ByeCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        storage.saveTaskList(tasks);
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
