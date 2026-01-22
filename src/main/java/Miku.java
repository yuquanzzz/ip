import java.util.*;

public class Miku {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Task> todolist = new ArrayList<>();

        Map<String, Command> commands = Map.of(
            "list", Command.LIST,
            "bye", Command.BYE,
            "mark", Command.MARK,
            "unmark", Command.UNMARK
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
            String input = sc.nextLine();
            Command command = commands.getOrDefault(input.split(" ")[0], Command.ADD);
            switch (command) {
                case LIST:
                    if (todolist.isEmpty()) {
                        System.out.println("\t____________________________________________________________");
                        System.out.println("\tYour to-do list is currently empty! " +
                                "Let's add some tasks to get started! (´꒳`)♡");
                        System.out.println("\t____________________________________________________________");
                        break;
                    }
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tHere are the tasks in your to-do list:");
                    for (int i = 1; i <= todolist.size(); i++) {
                        System.out.println("\t" + (i) + ". " + todolist.get(i-1));
                    }
                    System.out.println("\t____________________________________________________________");
                    break;
                case MARK:
                    int markIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                    todolist.get(markIndex).markAsDone();
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tGreat job! You've marked a task as done! (´꒳`)♡");
                    System.out.println("\t\t" + todolist.get(markIndex).toString());
                    System.out.println("\t____________________________________________________________");
                    break;
                case UNMARK:
                    markIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                    todolist.get(markIndex).markAsUndone();
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tNo worries! You've unmarked a task. Keep going! (´꒳`)♡");
                    System.out.println("\t\t" + todolist.get(markIndex).toString());
                    System.out.println("\t____________________________________________________________");
                    break;
                case BYE:
                    sc.close();
                    exit = true;
                    break;
                case ADD:
                    todolist.add(new Task(input));
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\tadded: " + input);
                    System.out.println("\t____________________________________________________________");
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
        ADD,
        MARK,
        UNMARK
    }
}