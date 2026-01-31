package miku.storage;

import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import miku.task.Deadline;
import miku.task.Event;
import miku.task.TaskList;
import miku.task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageTest {
    private Storage storage;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        storage = new Storage(tempDir.toString());
    }

    @Test
    void loadTaskList_noExistingFile_returnsEmptyTaskList() {
        TaskList taskList = storage.loadTaskList();
        assertNotNull(taskList);
        assertEquals(0, taskList.size());
    }

    @Test
    void saveAndLoadTaskList_mixedTaskTypes_preservesAllTaskTypes() {
        TaskList originalTaskList = new TaskList();
        originalTaskList.addTask(new Todo("todo task"));
        originalTaskList.addTask(new Deadline("deadline task", 
            LocalDateTime.of(2026, 12, 31, 23, 59)));
        originalTaskList.addTask(new Event("event task", 
            LocalDateTime.of(2026, 1, 1, 0, 0),
            LocalDateTime.of(2026, 1, 1, 12, 0)));
        
        storage.saveTaskList(originalTaskList);
        
        TaskList loadedTaskList = storage.loadTaskList();
        assertEquals(3, loadedTaskList.size());
    }

    @Test
    void saveAndLoadTaskList_tasksWithDifferentStatus_preservesStatus() throws Exception {
        TaskList originalTaskList = new TaskList();
        originalTaskList.addTask(new Todo("task 1"));
        originalTaskList.addTask(new Todo("task 2"));
        originalTaskList.markTask(0);
        
        storage.saveTaskList(originalTaskList);
        
        TaskList loadedTaskList = storage.loadTaskList();
        assertTrue(loadedTaskList.getTask(0).isDone());
        assertFalse(loadedTaskList.getTask(1).isDone());
    }

    @Test
    void saveAndLoadTaskList_emptyTaskList_preservesEmptyList() {
        TaskList emptyTaskList = new TaskList();
        storage.saveTaskList(emptyTaskList);
        
        TaskList loadedTaskList = storage.loadTaskList();
        assertEquals(0, loadedTaskList.size());
    }

    @Test
    void saveTaskList_multipleSaves_overwritesPreviousData() {
        TaskList firstTaskList = new TaskList();
        firstTaskList.addTask(new Todo("first task"));
        storage.saveTaskList(firstTaskList);
        
        TaskList secondTaskList = new TaskList();
        secondTaskList.addTask(new Todo("second task"));
        secondTaskList.addTask(new Todo("another task"));
        storage.saveTaskList(secondTaskList);
        
        TaskList loadedTaskList = storage.loadTaskList();
        assertEquals(2, loadedTaskList.size());
    }
}
