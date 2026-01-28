import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    private static final Path PATH = Paths.get("data", "taskList.ser");

    public static TaskList loadTaskList() {
        // no existing taskList, creating new taskList
        if (Files.notExists(PATH)) {
            return new TaskList();
        }
        
        // try to load existing taskList
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(PATH))) {
            Object obj = ois.readObject();
            // check if serialized object is a taskList
            if (obj instanceof TaskList) {
                return (TaskList) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            Ui.showError("Failed to load task list: " + e.getMessage(),  
                    "Creating new task list");
        }
        
        // failed to load existing taskList, creating new taskList
        return new TaskList();
    }

    public static void saveTaskList(TaskList taskList) {
        // try to save taskList
        try {
            Files.createDirectories(PATH.getParent());
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(PATH))) {
                oos.writeObject(taskList);
            }
        } catch (IOException e) {
            System.err.println("Failed to save task list: " + e.getMessage());
        }
    }
}
