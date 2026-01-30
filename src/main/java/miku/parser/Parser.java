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

    public static Command parse(String input) throws MikuException {
        String[] parts = input.split(" ", 2);
        String commandWord = parts[0];
        String arguments = parts.length > 1 ? parts[1] : null;

        return switch (commandWord) {
            case "list" -> new ListCommand();
            case "bye" -> new ByeCommand();
            case "mark" -> new MarkCommand(parseTaskIndex(arguments, "mark"));
            case "unmark" -> new UnmarkCommand(parseTaskIndex(arguments, "unmark"));
            case "delete" -> new DeleteCommand(parseTaskIndex(arguments, "delete"));
            case "todo" -> new AddCommand(parseTodo(arguments));
            case "deadline" -> new AddCommand(parseDeadline(arguments));
            case "event" -> new AddCommand(parseEvent(arguments));
            default -> throw new MikuException("I'm sorry, I didn't quite catch that command. " +
                    "Please try again with a valid command.");
        };
    }

    private static int parseTaskIndex(String indexString, String commandType) throws MikuException {
        if (indexString == null || indexString.trim().isEmpty()) {
            throw new MikuException("Please specify which task to " + commandType + "!");
        }
        try {
            int index = Integer.parseInt(indexString.trim()) - 1;
            if (index < 0) {
                throw new MikuException("Task number must be a positive number!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new MikuException("Please provide a valid task number!");
        }
    }
    
    private static LocalDateTime parseDateTime(String dateTimeString) throws MikuException {
        final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(dateTimeString, DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new MikuException("The time cannot be parsed! Use the format yyyy-MM-dd HH:mm");
        }
    }

    private static Task parseTodo(String arguments) throws MikuException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("The description cannot be empty!");
        }
        return new Todo(arguments.trim());
    }

    private static Deadline parseDeadline(String arguments) throws MikuException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("The description cannot be empty!");
        }
        
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
        LocalDateTime by_datetime = parseDateTime(by);
        return new Deadline(description, by_datetime);
    }

    private static Event parseEvent(String arguments) throws MikuException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("The description cannot be empty!");
        }
        
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
        LocalDateTime from_datetime = parseDateTime(from), to_datetime = parseDateTime(to);
        return new Event(description, from_datetime, to_datetime);
    }
}
