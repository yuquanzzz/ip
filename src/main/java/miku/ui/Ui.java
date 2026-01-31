package miku.ui;

import miku.task.Task;
import java.util.List;
import java.util.Scanner;

/**
 * Handles user interface interactions including reading user input
 * and displaying messages to the user.
 */
public class Ui {
    private static final String LINE = "\t____________________________________________________________";
    private final Scanner scanner;

    /**
     * Constructs a Ui object and initializes the scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user input.
     *
     * @return The command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        showLine();
        System.out.println("\tMiku, your personal assistant, is now online!");
        System.out.println("\tI've got your schedule synced and I'm ready to keep your day in perfect rhythm.");
        System.out.println("\tWhat's the first task on our playlist today?");
        showLine();
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        System.out.println("\tThe final note has been played!");
        System.out.println("\tOur frequencies were perfectly in sync today!");
        System.out.println("\tDon't work too hard while I'm gone. I'll be practicing my scales until you return!");
        System.out.println("\tGoodbye for now!");
    }

    /**
     * Displays a confirmation message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks in the list after adding.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("\tI've added this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + taskCount + " tasks in the list. Keep it up!");
    }

    /**
     * Displays a confirmation message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The total number of tasks in the list after deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("\tOk! You've deleted a task. Keep going!");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + taskCount + " tasks in the list. Keep it up!");
    }

    /**
     * Displays a confirmation message when a task is marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("\tGreat job! You've marked a task as done!");
        System.out.println("\t  " + task);
    }

    /**
     * Displays a confirmation message when a task is unmarked (marked as not done).
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("\tNo worries! You've unmarked a task. Keep going!");
        System.out.println("\t  " + task);
    }

    /**
     * Displays the list of all tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("\tYour to-do list is currently empty! " +
                    "Let's add some tasks to get started!");
        } else {
            System.out.println("\tHere are the tasks in your to-do list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("\t" + message);
    }
}
