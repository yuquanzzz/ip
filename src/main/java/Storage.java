import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    
    public static TaskList loadTaskList(String storageDir) {
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
            Ui.showError("Failed to load task list: " + e.getMessage(),  
                    "Creating new task list");
        }
        
        // failed to load existing taskList, creating new taskList
        return new TaskList();
    }

    public static void saveTaskList(String storageDir, TaskList taskList) {
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
