import java.util.Scanner;

public class Miku {
    private final Ui ui;
    private final TaskList taskList;

    public Miku() {
        this.ui = new Ui();
        this.taskList = new TaskList();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        ui.showWelcome();

        boolean exit = false;
        while (!exit) {
            String input = sc.nextLine();
            Parser.Command command = Parser.parseCommand(input);
            String[] parsedInput = Parser.parseInput(input);

            switch (command) {
                case LIST:
                    handleList();
                    break;
                case TODO:
                case DEADLINE:
                case EVENT:
                    handleAddTask(command, parsedInput[1]);
                    break;
                case MARK:
                    handleMark(parsedInput[1]);
                    break;
                case UNMARK:
                    handleUnmark(parsedInput[1]);
                    break;
                case BYE:
                    exit = true;
                    break;
            }
        }

        ui.showGoodbye();
        sc.close();
    }

    private void handleList() {
        ui.showTaskList(taskList.getTasks());
    }

    private void handleAddTask(Parser.Command command, String arguments) {
        Task task = Parser.parseTask(command, arguments);
        taskList.addTask(task);
        ui.showTaskAdded(task, taskList.size());
    }

    private void handleMark(String indexString) {
        int index = Parser.parseTaskIndex(indexString);
        taskList.markTask(index);
        ui.showTaskMarked(taskList.getTask(index));
    }

    private void handleUnmark(String indexString) {
        int index = Parser.parseTaskIndex(indexString);
        taskList.unmarkTask(index);
        ui.showTaskUnmarked(taskList.getTask(index));
    }

    public static void main(String[] args) {
        new Miku().run();
    }
}