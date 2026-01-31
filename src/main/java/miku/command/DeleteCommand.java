package miku.command;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.Task;
import miku.task.TaskList;
import miku.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException {
        Task task = tasks.getTask(index);
        tasks.deleteTask(index);
        storage.saveTaskList(tasks);
        ui.showTaskDeleted(task, tasks.size());
    }
}
