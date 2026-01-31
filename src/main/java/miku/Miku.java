package miku;

import java.io.File;

import miku.command.Command;
import miku.exception.MikuException;
import miku.parser.Parser;
import miku.storage.Storage;
import miku.task.TaskList;
import miku.ui.Ui;

public class Miku {
    private static final String STORAGE_DIR = System.getProperty("user.home") + File.separator
            + ".miku" + File.separator;     // saves user data under .miku folder

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Miku() {
        ui = new Ui();
        storage = new Storage(STORAGE_DIR);
        tasks = storage.loadTaskList();
    }

    public static void main(String[] args) {
        new Miku().run();
    }

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
