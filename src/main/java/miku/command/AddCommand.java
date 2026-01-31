package miku.command;

import miku.storage.Storage;
import miku.task.Task;
import miku.task.TaskList;
import miku.ui.Ui;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private final Task task;
    
    /**
     * Constructs an AddCommand with the specified task.
     *
     * @param task The task to be added to the task list.
     */
    public AddCommand(Task task) {
        this.task = task;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.addTask(task);
        storage.saveTaskList(tasks);
        ui.showTaskAdded(task, tasks.size());
    }
}
