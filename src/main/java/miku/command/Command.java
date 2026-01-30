package miku.command;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.Ui;

public abstract class Command {
    
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException;
    
    public boolean isExit() {
        return false;
    }
}
