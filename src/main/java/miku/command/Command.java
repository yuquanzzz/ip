package miku.command;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.Ui;

/**
 * Represents an abstract command that can be executed.
 * All specific command types should extend this class.
 */
public abstract class Command {
    
    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks The task list to operate on.
     * @param ui The user interface for displaying messages.
     * @param storage The storage for persisting task data.
     * @throws MikuException If an error occurs during command execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException;
    
    /**
     * Checks if this command causes the application to exit.
     *
     * @return true if the application should exit after this command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
