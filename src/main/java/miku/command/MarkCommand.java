package miku.command;

import miku.exception.MikuException;
import miku.storage.Storage;
import miku.task.Task;
import miku.task.TaskList;
import miku.ui.Ui;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MikuException {
        tasks.markTask(index);
        Task task = tasks.getTask(index);
        storage.saveTaskList(tasks);
        ui.showTaskMarked(task);
    }
}
