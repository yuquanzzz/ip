package miku.parser;

import miku.command.AddCommand;
import miku.command.ByeCommand;
import miku.command.Command;
import miku.command.DeleteCommand;
import miku.command.ListCommand;
import miku.command.MarkCommand;
import miku.command.UnmarkCommand;
import miku.exception.MikuException;
import miku.task.Deadline;
import miku.task.Event;
import miku.task.Task;
import miku.task.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Command parseCommand(String input) {
        String commandWord = input.split(" ", 2)[0];
        return switch (commandWord) {
            case "list" -> Command.LIST;
            case "bye" -> Command.BYE;
            case "mark" -> Command.MARK;
            case "unmark" -> Command.UNMARK;
            case "todo" -> Command.TODO;
            case "deadline" -> Command.DEADLINE;
            case "event" -> Command.EVENT;
            case "delete" -> Command.DELETE;
            default -> Command.UNKNOWN;
        };
    }

    public static String[] parseInput(String input) {
        return input.split(" ", 2);
    }

    public static int parseTaskIndex(String indexString) throws MikuException {
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index <= 0) {
                throw new MikuException("Task number must be a positive number!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new MikuException("Please provide a valid task number!");
        }
    }

    public static Task parseTask(Command command, String arguments) throws MikuException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("The description cannot be empty!");
        }

        return switch (command) {
            case TODO -> new Todo(arguments.trim());
            case DEADLINE -> parseDeadline(arguments);
            case EVENT -> parseEvent(arguments);
            default -> null;
        };
    }

    private static Deadline parseDeadline(String arguments) throws MikuException {
        int byIndex = arguments.indexOf("/by ");
        if (byIndex == -1) {
            throw new MikuException("Please specify the deadline using /by <time>!");
        }
        String description = arguments.substring(0, byIndex).trim();
        if (description.isEmpty()) {
            throw new MikuException("The description of a deadline cannot be empty!");
        }
        String by = arguments.substring(byIndex + 4).trim();
        if (by.isEmpty()) {
            throw new MikuException("The deadline time cannot be empty!");
        }
        LocalDateTime by_datetime;
        try {
            by_datetime = LocalDateTime.parse(by, DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new MikuException("The time cannot be parsed! Use the format yyyy-MM-dd HH:mm");
        }
        return new Deadline(description, by_datetime);
    }

    private static Event parseEvent(String arguments) throws MikuException {
        int fromIndex = arguments.indexOf("/from ");
        int toIndex = arguments.indexOf(" /to ");

        if (fromIndex == -1) {
            throw new MikuException("Please specify the start time using /from <time>!");
        }
        if (toIndex == -1) {
            throw new MikuException("Please specify the end time using /to <time>!");
        }

        String description = arguments.substring(0, fromIndex).trim();
        if (description.isEmpty()) {
            throw new MikuException("The description of an event cannot be empty!");
        }
        String from = arguments.substring(fromIndex + 6, toIndex).trim();
        if (from.isEmpty()) {
            throw new MikuException("The start time cannot be empty!");
        }
        String to = arguments.substring(toIndex + 5).trim();
        if (to.isEmpty()) {
            throw new MikuException("The end time cannot be empty!");
        }
        LocalDateTime from_datetime, to_datetime;
        try {
            from_datetime = LocalDateTime.parse(from, DATETIME_FORMAT);
            to_datetime = LocalDateTime.parse(to, DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new MikuException("The time cannot be parsed! Use the format yyyy-MM-dd HH:mm");
        }
        return new Event(description, from_datetime, to_datetime);
    }
}
