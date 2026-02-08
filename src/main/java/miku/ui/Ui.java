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
        printLine("\tMiku Miku ni shite ageru! ♪ (^_^)☆");
        printLine("\tI've finished calibrating my voice banks and syncing your schedule.");
        printLine("\tWhat’s on the setlist for today? Let's make it a hit!");
        showDividerLine();
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        printLine("\tThe final note has been played! (T_T)┛");
        printLine("\tI'm heading back to the digital world to practice my scales.");
        printLine("\tDon't let your productivity drop to 0% while I'm gone! Bye-bye!");
    }

    /**
     * Displays a confirmation message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks in the list after adding.
     */
    public void showTaskAdded(Task task, int taskCount) {
        printLine("\tAdded to the playlist! ✨");
        printLine("\t  " + task);
        printLine("\tThat makes " + taskCount + " tasks in total. We’re building a whole album here!");
    }

    /**
     * Displays a confirmation message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The total number of tasks in the list after deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        printLine("\tPoof! That task didn't make the final cut. (waste of memory anyway~)");
        printLine("\t  " + task);
        printLine("\tYou've got " + taskCount + " tasks left. Keep up the rhythm!");
    }

    /**
     * Displays a confirmation message when a task is marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        printLine("\tFULL COMBO!! ⭐ You finished the task!");
        printLine("\t  " + task);
        printLine("\tI'll give that performance a 10/10!");
    }

    /**
     * Displays a confirmation message when a task is unmarked (marked as not done).
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        printLine("\tEh? An encore? (・_・;)");
        printLine("\tI've moved this back to the 'incomplete' list for you:");
        printLine("\t  " + task);
    }

    /**
     * Displays the list of all tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            printLine("\tYour task list is as empty as a stadium before a concert!");
            printLine("\tLet's add something so I have a reason to stay online!");
        } else {
            printLine("\tScanning memory banks... Here’s our current setlist:");
            for (int i = 0; i < tasks.size(); i++) {
                printLine("\t" + (i + 1) + ". " + tasks.get(i));
            }
            printLine("\tDon't get stage fright, you can do it!");
        }
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        printLine("\tSYSTEM ERROR! *Static sounds* ┌(;──)┐");
        printLine("\tMy software is lagging because: " + message);
        printLine("\tFix it quickly, or I might start singing off-key!");
    }

    /**
     * Displays the search results for tasks matching a keyword.
     *
     * @param tasks The list of matching tasks to display.
     * @param keyword The keyword that was searched for.
     */
    public void showFindResults(List<Task> tasks, String keyword) {
        if (tasks.isEmpty()) {
            printLine("\tI searched every frequency, but I couldn't find \"" + keyword + "\" anywhere!");
        } else {
            printLine("\tFound 'em! These tasks hit the right notes for \"" + keyword + "\":");
            for (int i = 0; i < tasks.size(); i++) {
                printLine("\t" + (i + 1) + ". " + tasks.get(i));
            }
        }
    }
}
