package miku.storage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import miku.task.TaskList;

public class Storage {
    private final String storageDir;

    public Storage(String storageDir) {
        this.storageDir = storageDir;
    }

    public TaskList loadTaskList() {
        Path path = Paths.get(storageDir, "taskList.ser");
        // no existing taskList, creating new taskList
        if (Files.notExists(path)) {
            return new TaskList();
        }

        // try to load existing taskList
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            Object obj = ois.readObject();
            // check if serialized object is a taskList
            if (obj instanceof TaskList) {
                return (TaskList) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load task list: " + e.getMessage());
            System.err.println("Creating new task list");
        }

        // failed to load existing taskList, creating new taskList
        return new TaskList();
    }

    /**
     * Saves the task list to storage.
     * Creates the storage directory if it does not exist.
     *
     * @param taskList The task list to be saved.
     */
    public void saveTaskList(TaskList taskList) {
        Path path = Paths.get(storageDir, "taskList.ser");
        // try to save taskList
        try {
            Files.createDirectories(path.getParent());
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
                oos.writeObject(taskList);
            }
        } catch (IOException e) {
            System.err.println("Failed to save task list: " + e.getMessage());
        }
    }
}
