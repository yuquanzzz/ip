import java.util.*;

public class Miku {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Task> taskList = new ArrayList<>();

        Map<String, Command> commands = Map.of(
            "list", Command.LIST,
            "bye", Command.BYE,
            "mark", Command.MARK,
            "unmark", Command.UNMARK,
            "todo", Command.TODO,
            "deadline", Command.DEADLINE,
            "event", Command.EVENT
        );

        // Display welcome message
        System.out.println("\t____________________________________________________________");
        System.out.println("\t「Miku-miku ni shite ageru! ♪」");
        System.out.println("\tHatsune Miku, your personal assistant, is now online! (´꒳`)♡");
        System.out.println("\tI've got your schedule synced and I'm ready to keep your day in perfect rhythm.");
        System.out.println("\tWhat’s the first task on our playlist today?");
        System.out.println("\t____________________________________________________________");

        // Echos back user input until "bye" is entered
        boolean exit = false;
        while (!exit) {
            String[] input = sc.nextLine().split(" ", 2);
            Command command = commands.get(input[0]);
            switch (command) {
                case LIST:
                    if (taskList.isEmpty()) {
                        System.out.println("\t____________________________________________________________");
                        System.out.println("\tYour to-do list is currently empty! " +
                                "Let's add some tasks to get started! (´꒳`)♡");
                        System.out.println("\t____________________________________________________________");
                        break;
                    }
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tHere are the tasks in your to-do list:");
                    for (int i = 1; i <= taskList.size(); i++) {
                        System.out.println("\t" + (i) + ". " + taskList.get(i-1));
                    }
                    System.out.println("\t____________________________________________________________");
                    break;
                case TODO:
                    Task todo = new Todo(input[1]);
                    taskList.add(todo);
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tI've added this task:");
                    System.out.println("\t\t" + todo);
                    System.out.println("\tNow you have " + taskList.size() + " tasks in the list. Keep it up! (´꒳`)♡");
                    System.out.println("\t____________________________________________________________");
                    break;
                case DEADLINE:
                    String by = input[1].substring(input[1].indexOf("/by ") + 4);
                    Task deadline = new Deadline(input[1].substring(0, input[1].indexOf("/by ") - 1), by);
                    taskList.add(deadline);
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tI've added this task:");
                    System.out.println("\t\t" + deadline);
                    System.out.println("\tNow you have " + taskList.size() + " tasks in the list. Keep it up! (´꒳`)♡");
                    System.out.println("\t____________________________________________________________");
                    break;
                case EVENT:
                    String from = input[1].substring(input[1].indexOf("/from ") + 6, input[1].indexOf(" /to "));
                    String to = input[1].substring(input[1].indexOf("/to ") + 4);
                    Task event = new Event(input[1].substring(0, input[1].indexOf("/from ") - 1), from, to);
                    taskList.add(event);
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tI've added this task:");
                    System.out.println("\t\t" + event);
                    System.out.println("\tNow you have " + taskList.size() + " tasks in the list. Keep it up! (´꒳`)♡");
                    System.out.println("\t____________________________________________________________");
                    break;
                case MARK:
                    int markIndex = Integer.parseInt(input[1]) - 1;
                    taskList.get(markIndex).markAsDone();
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tGreat job! You've marked a task as done! (´꒳`)♡");
                    System.out.println("\t\t" + taskList.get(markIndex).toString());
                    System.out.println("\t____________________________________________________________");
                    break;
                case UNMARK:
                    int unmarkIndex = Integer.parseInt(input[1]) - 1;
                    taskList.get(unmarkIndex).markAsUndone();
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tNo worries! You've unmarked a task. Keep going! (´꒳`)♡");
                    System.out.println("\t\t" + taskList.get(unmarkIndex).toString());
                    System.out.println("\t____________________________________________________________");
                    break;
                case BYE:
                    sc.close();
                    exit = true;
                    break;
            }
        }

        // Display goodbye message
        System.out.println("\t____________________________________________________________");
        System.out.println("\t「The final note has been played!」");
        System.out.println("\tOur frequencies were perfectly in sync today! (≧∇≦)/");
        System.out.println("\tDon't work too hard while I'm gone. I'll be practicing my scales until you return!");
        System.out.println("\tGoodbye for now!");
        System.out.println("\t____________________________________________________________");
    }

    enum Command {
        LIST,
        BYE,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
    }
}