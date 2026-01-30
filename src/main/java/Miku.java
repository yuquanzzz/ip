import java.io.File;
import java.util.Scanner;

public class Miku {
    static final String STORAGE_DIR = System.getProperty("user.home") + File.separator + ".miku" + File.separator;
    private final TaskList taskList;

    public Miku() {
        this.taskList = Storage.loadTaskList(STORAGE_DIR);
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        Ui.showWelcome();

        boolean exit = false;
        while (!exit) {
            try {
                String input = sc.nextLine();
                Command command = Parser.parseCommand(input);
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
                case UNMARK:
                case DELETE:
                    validateTaskIndex(parsedInput, command);
                    handleTaskAction(command, parsedInput[1]);
                    break;
                case BYE:
                    exit = true;
                    break;
                case UNKNOWN:
                    throw new MikuException("I'm sorry, I didn't quite catch that command. " +
                            "Please try again with a valid command.");
                }
            } catch (MikuException e) {
                Ui.showError(e.getMessage());
            }
        }

        Storage.saveTaskList(STORAGE_DIR, taskList);
        Ui.showGoodbye();
        sc.close();
    }

    private void handleList() {
        Ui.showTaskList(taskList.getTasks());
    }

    private void handleAddTask(Command command, String arguments) throws MikuException {
        Task task = Parser.parseTask(command, arguments);
        taskList.addTask(task);
        Storage.saveTaskList(STORAGE_DIR, taskList);
        Ui.showTaskAdded(task, taskList.size());
    }

    private void validateTaskIndex(String[] parsedInput, Command command) throws MikuException {
        if (parsedInput.length < 2) {
            String action = command.toString().toLowerCase();
            throw new MikuException("Please specify which task to " + action + "!");
        }
    }

    private void handleTaskAction(Command command, String indexString) throws MikuException {
        int index = Parser.parseTaskIndex(indexString);
        Task task = taskList.getTask(index);

        switch (command) {
        case MARK:
            taskList.markTask(index);
            Storage.saveTaskList(STORAGE_DIR, taskList);
            Ui.showTaskMarked(task);
            break;
        case UNMARK:
            taskList.unmarkTask(index);
            Storage.saveTaskList(STORAGE_DIR, taskList);
            Ui.showTaskUnmarked(task);
            break;
        case DELETE:
            taskList.deleteTask(index);
            Storage.saveTaskList(STORAGE_DIR, taskList);
            Ui.showTaskDeleted(task, taskList.size());
            break;
        }
    }

    public static void main(String[] args) {
        new Miku().run();
    }
}