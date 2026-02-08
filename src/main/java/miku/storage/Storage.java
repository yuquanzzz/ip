package miku.storage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import miku.exception.MikuException;
import miku.task.TaskList;

/**
 * Handles the loading and saving of tasks to persistent storage.
 * Tasks are serialized and stored as a file in the specified directory.
 */
public class Storage {
    private final String storageDir;

    /**
     * Constructs a new Storage instance with the specified directory.
     *
     * @param storageDir The directory path where task data will be stored.
     */
    public Storage(String storageDir) {
        this.storageDir = storageDir;
    }

    /**
     * Loads the task list from storage.
     * If no existing task list file is found, returns a new empty task list.
     * If loading fails due to IO or deserialization errors, returns a new empty task list.
     *
     * @return The loaded TaskList, or a new empty TaskList if loading fails.
     */
    public TaskList loadTaskList() {
        Path path = Paths.get(storageDir, "taskList.ser");
        // no existing task list, creating new task list
        if (Files.notExists(path)) {
            return new TaskList();
        }

        // try to load existing task list
        TaskList loadedObj = loadFromStorage(path);

        // return existing task list if possible, else return a new task list
        return loadedObj != null ? loadedObj : new TaskList();
    }

    private static TaskList loadFromStorage(Path path) {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            Object loadedObj = ois.readObject();
            // check if serialized object is a task list
            if (loadedObj instanceof TaskList) {
                return (TaskList) loadedObj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\tFailed to load existing task list: " + e.getMessage());
            System.out.println("\tCreating new task list instead");
        }
        return null;
    }

    /**
     * Saves the task list to storage.
     * Creates the storage directory if it does not exist.
     *
     * @param taskList The task list to be saved.
     * @throws MikuException If task list cannot be saved
     */
    public void saveTaskList(TaskList taskList) throws MikuException {
        Path path = Paths.get(storageDir, "taskList.ser");
        // try to save taskList
        try {
            Files.createDirectories(path.getParent());
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
                oos.writeObject(taskList);
            }
        } catch (IOException e) {
            throw new MikuException("Failed to save task list: " + e.getMessage());
        }
    }
}
