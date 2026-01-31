package miku.ui;

import java.util.List;
import java.util.Scanner;

import miku.task.Task;

public class Ui {
    private static final String LINE = "\t____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println("\tMiku, your personal assistant, is now online!");
        System.out.println("\tI've got your schedule synced and I'm ready to keep your day in perfect rhythm.");
        System.out.println("\tWhat's the first task on our playlist today?");
        showLine();
    }

    public void showGoodbye() {
        System.out.println("\tThe final note has been played!");
        System.out.println("\tOur frequencies were perfectly in sync today!");
        System.out.println("\tDon't work too hard while I'm gone. I'll be practicing my scales until you return!");
        System.out.println("\tGoodbye for now!");
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("\tI've added this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + taskCount + " tasks in the list. Keep it up!");
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("\tOk! You've deleted a task. Keep going!");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + taskCount + " tasks in the list. Keep it up!");
    }

    public void showTaskMarked(Task task) {
        System.out.println("\tGreat job! You've marked a task as done!");
        System.out.println("\t  " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("\tNo worries! You've unmarked a task. Keep going!");
        System.out.println("\t  " + task);
    }

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

    public void showError(String message) {
        System.out.println("\t" + message);
    }
}
