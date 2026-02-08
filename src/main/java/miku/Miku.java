package miku;

import java.io.File;

import miku.command.Command;
import miku.exception.MikuException;
import miku.parser.Parser;
import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.TextUi;


/**
 * Main class for the Miku chatbot application.
 * Miku is a task management chatbot that can handle todos, deadlines, and events.
 * It supports saving and loading tasks from a file for persistence.
 */
public class Miku {
    // default save directory is $HOME/.miku
    private static final String STORAGE_DIR =
            System.getProperty("user.home") + File.separator + ".miku" + File.separator;

    private final Storage storage;
    private final TaskList tasks;
    private final TextUi ui;

    /**
     * Constructs a new Miku chatbot instance.
     * Initialises the UI, storage, and parser components.
     * Attempts to load existing tasks from the specified file path.
     * If loading fails, starts with an empty task list.
     *
     * @param filePath The file path where tasks are stored and loaded from.
     */
    public Miku(String filePath) {
        ui = new TextUi();
        storage = new Storage(filePath);
        tasks = storage.loadTaskList();
    }

    /**
     * Entry point of the application.
     * Creates a new Miku instance and starts the chatbot.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Miku(STORAGE_DIR).run();
    }

    /**
     * Runs the main loop of the Miku chatbot.
     * Displays a welcome message, then continuously reads and processes user commands
     * until an exit command is received.
     * Handles any exceptions by displaying error messages to the user.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (MikuException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }
}
