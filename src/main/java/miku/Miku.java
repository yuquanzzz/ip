package miku;

import java.io.File;

import miku.command.Command;
import miku.exception.MikuException;
import miku.parser.Parser;
import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.Gui;
import miku.ui.Ui;


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
    private final Ui ui;
    private boolean isExit;
    private String commandType;

    /**
     * Constructs a new Miku chatbot instance.
     * Initialises the UI, storage, and parser components.
     * Attempts to load existing tasks from the specified file path.
     * If loading fails, starts with an empty task list.
     *
     * @param filePath The file path where tasks are stored and loaded from.
     */
    public Miku(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = storage.loadTaskList();
        isExit = false;
    }

    /**
     * Public accessor for the default storage directory.
     */
    public static String getDefaultStorageDir() {
        return STORAGE_DIR;
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
        isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showDividerLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (MikuException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showDividerLine();
            }
        }
    }

    /**
     * Returns the welcome banner for GUI.
     */
    public String getWelcomeMessage() {
        Gui gui = new Gui();
        gui.showWelcome();
        return gui.getResponse();
    }

    /**
     * Processes a single user input and returns Miku's response for GUI.
     */
    public String getResponse(String input) {
        Gui gui = new Gui();
        try {
            Command command = Parser.parse(input);
            command.execute(tasks, gui, storage);
            commandType = command.getClass().getSimpleName();
            isExit = command.isExit();
        } catch (MikuException e) {
            commandType = e.getClass().getSimpleName();
            gui.showError(e.getMessage());
        }
        return gui.getResponse();
    }

    public String getCommandType() {
        return commandType;
    }

    public boolean isExit() {
        return isExit;
    }
}
