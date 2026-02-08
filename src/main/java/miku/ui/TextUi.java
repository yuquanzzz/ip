package miku.ui;

import java.util.List;
import java.util.Scanner;

import miku.task.Task;

/**
 * Handles user interface interactions including reading user input
 * and displaying messages to the user.
 */
public class Ui {
    private static final String DIVIDER_LINE = "\t____________________________________________________________";
    private final Scanner scanner;

    /**
     * Constructs an Ui object and initialises the scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    protected void printLine(String message) {
        System.out.println(message);
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
    public void showDividerLine() {
        printLine(DIVIDER_LINE);
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        showDividerLine();
        printLine("\tMiku, your personal assistant, is now online!");
        printLine("\tI've got your schedule synced and I'm ready to keep your day in perfect rhythm.");
        printLine("\tWhat's the first task on our playlist today?");
        showDividerLine();
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        printLine("\tThe final note has been played!");
        printLine("\tOur frequencies were perfectly in sync today!");
        printLine("\tDon't work too hard while I'm gone. I'll be practicing my scales until you return!");
        printLine("\tGoodbye for now!");
    }

    /**
     * Displays a confirmation message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks in the list after adding.
     */
    public void showTaskAdded(Task task, int taskCount) {
        printLine("\tI've added this task:");
        printLine("\t  " + task);
        printLine("\tNow you have " + taskCount + " tasks in the list. Keep it up!");
    }

    /**
     * Displays a confirmation message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The total number of tasks in the list after deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        printLine("\tOk! You've deleted a task. Keep going!");
        printLine("\t  " + task);
        printLine("\tNow you have " + taskCount + " tasks in the list. Keep it up!");
    }

    /**
     * Displays a confirmation message when a task is marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        printLine("\tGreat job! You've marked a task as done!");
        printLine("\t  " + task);
    }

    /**
     * Displays a confirmation message when a task is unmarked (marked as not done).
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        printLine("\tNo worries! You've unmarked a task. Keep going!");
        printLine("\t  " + task);
    }

    /**
     * Displays the list of all tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            printLine("\tYour to-do list is currently empty! "
                    + "Let's add some tasks to get started!");
        } else {
            printLine("\tHere are the tasks in your to-do list:");
            for (int i = 0; i < tasks.size(); i++) {
                printLine("\t" + (i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        printLine("\t" + message);
    }

    /**
     * Displays the search results for tasks matching a keyword.
     *
     * @param tasks The list of matching tasks to display.
     * @param keyword The keyword that was searched for.
     */
    public void showFindResults(List<Task> tasks, String keyword) {
        if (tasks.isEmpty()) {
            printLine("\tNo tasks found matching \"" + keyword + "\".");
        } else {
            printLine("\tHere are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                printLine("\t" + (i + 1) + ". " + tasks.get(i));
            }
        }
    }
}
