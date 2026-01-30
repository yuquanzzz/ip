package miku.command;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.Task;
import miku.task.TaskList;
import miku.ui.Ui;

public class UnmarkCommand extends Command {
    private final int index;
    
    public UnmarkCommand(int index) {
        this.index = index;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException {
        tasks.unmarkTask(index);
        Task task = tasks.getTask(index);
        storage.saveTaskList(tasks);
        ui.showTaskUnmarked(task);
    }
}
