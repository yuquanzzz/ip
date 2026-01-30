import java.util.Scanner;

public class Miku {
    static final String STORAGE_DIR = "data/";
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
                    case DELETE:
                        if (parsedInput.length < 2) {
                            throw new MikuException("Please specify which task to delete!");
                        }
                        handleDeleteTask(parsedInput[1]);
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
        Storage.saveTaskList(taskList);
    }

    private void handleDeleteTask(String indexString) throws MikuException {
        int index = Parser.parseTaskIndex(indexString);
        Task task = taskList.getTask(index);
        taskList.deleteTask(index);
        Storage.saveTaskList(STORAGE_DIR, taskList);
        Ui.showTaskDeleted(task, taskList.size());
        Storage.saveTaskList(taskList);
    }

    private void handleMark(String indexString) throws MikuException {
        int index = Parser.parseTaskIndex(indexString);
        taskList.markTask(index);
        Storage.saveTaskList(STORAGE_DIR, taskList);
        Ui.showTaskMarked(taskList.getTask(index));
        Storage.saveTaskList(taskList);
    }

    private void handleUnmark(String indexString) throws MikuException {
        int index = Parser.parseTaskIndex(indexString);
        taskList.unmarkTask(index);
        Storage.saveTaskList(STORAGE_DIR, taskList);
        Ui.showTaskUnmarked(taskList.getTask(index));
        Storage.saveTaskList(taskList);
    }

    public static void main(String[] args) {
        new Miku().run();
    }
}