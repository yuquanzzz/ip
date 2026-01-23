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
            try {
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
                        handleAddTask(command, parsedInput.length > 1 ? parsedInput[1] : null);
                        break;
                    case MARK:
                        if (parsedInput.length < 2) {
                            throw new MikuException("Please specify which task to mark!");
                        }
                        handleMark(parsedInput[1]);
                        break;
                    case UNMARK:
                        if (parsedInput.length < 2) {
                            throw new MikuException("Please specify which task to unmark!");
                        }
                        handleUnmark(parsedInput[1]);
                        break;
                    case BYE:
                        exit = true;
                        break;
                    case UNKNOWN:
                        throw new MikuException("I'm sorry, I didn't quite catch that command. Please try again with a valid command.");
                }
            } catch (MikuException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.showGoodbye();
        sc.close();
    }

    private void handleList() {
        ui.showTaskList(taskList.getTasks());
    }

    private void handleAddTask(Parser.Command command, String arguments) throws MikuException {
        Task task = Parser.parseTask(command, arguments);
        taskList.addTask(task);
        ui.showTaskAdded(task, taskList.size());
    }

    private void handleMark(String indexString) throws MikuException {
        int index = Parser.parseTaskIndex(indexString);
        taskList.markTask(index);
        ui.showTaskMarked(taskList.getTask(index));
    }

    private void handleUnmark(String indexString) throws MikuException {
        int index = Parser.parseTaskIndex(indexString);
        taskList.unmarkTask(index);
        ui.showTaskUnmarked(taskList.getTask(index));
    }

    public static void main(String[] args) {
        new Miku().run();
    }
}