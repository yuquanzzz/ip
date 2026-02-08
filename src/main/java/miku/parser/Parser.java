package miku.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import miku.command.AddCommand;
import miku.command.ByeCommand;
import miku.command.Command;
import miku.command.DeleteCommand;
import miku.command.FindCommand;
import miku.command.ListCommand;
import miku.command.MarkCommand;
import miku.command.UnmarkCommand;
import miku.exception.MikuException;
import miku.task.Deadline;
import miku.task.Event;
import miku.task.Task;
import miku.task.Todo;

/**
 * Parses user input and converts it into executable commands.
 */
public class Parser {

    private static final Pattern DEADLINE_BY = Pattern.compile("\\s*/by\\b\\s*");
    private static final Pattern EVENT_FROM = Pattern.compile("\\s*/from\\b\\s*");
    private static final Pattern EVENT_TO = Pattern.compile("\\s*/to\\b\\s*");

    /**
     * Parses the user input string and returns the corresponding command.
     *
     * @param input The user input string to parse.
     * @return The command corresponding to the user input.
     * @throws MikuException If the input is invalid or cannot be parsed.
     */
    public static Command parse(String input) throws MikuException {
        ParsedInput parsedInput = splitCommandAndArgs(input);

        return switch (parsedInput.command()) {
        case "list" -> new ListCommand();
        case "bye" -> new ByeCommand();
        case "mark" -> new MarkCommand(parseTaskIndex(parsedInput.arguments(), "mark"));
        case "unmark" -> new UnmarkCommand(parseTaskIndex(parsedInput.arguments(), "unmark"));
        case "delete" -> new DeleteCommand(parseTaskIndex(parsedInput.arguments(), "delete"));
        case "todo" -> new AddCommand(parseTodo(parsedInput.arguments()));
        case "deadline" -> new AddCommand(parseDeadline(parsedInput.arguments()));
        case "event" -> new AddCommand(parseEvent(parsedInput.arguments()));
        case "find" -> new FindCommand(parseKeyword(parsedInput.arguments()));
        default -> throw new MikuException("Invalid command, please try again with a valid command!");
        };
    }

    // helper method to split raw input to command and args, with validation checks
    private static ParsedInput splitCommandAndArgs(String input) throws MikuException {
        // checks if input is empty
        if (input == null || input.trim().isEmpty()) {
            throw new MikuException("Command entered is empty!");
        }
        String trimmedInput = input.trim();

        // find first whitespace to split between command and arguments
        int firstSpaceIndex = -1;
        for (int i = 0; i < trimmedInput.length(); i++) {
            if (Character.isWhitespace(trimmedInput.charAt(i))) {
                firstSpaceIndex = i;
                break;
            }
        }

        String command;
        String arguments;

        if (firstSpaceIndex == -1) {
            // whitespace not found, treat entire input as command
            command = trimmedInput.toLowerCase();
            arguments = null;
        } else {
            // whitespace found, split input to command and arguments
            command = trimmedInput.substring(0, firstSpaceIndex).toLowerCase();
            arguments = trimmedInput.substring(firstSpaceIndex + 1).trim();
            if (arguments.isEmpty()) {
                arguments = null;
            }
        }
        return new ParsedInput(command, arguments);
    }

    private record ParsedInput(String command, String arguments) {
    }

    private static int parseTaskIndex(String indexString, String commandType) throws MikuException {
        if (indexString == null || indexString.trim().isEmpty()) {
            throw new MikuException("Please specify which task to " + commandType + "!");
        }
        try {
            int index = Integer.parseInt(indexString.trim()) - 1; // 0-based indexing
            if (index < 0) {
                throw new MikuException("Task number must be a positive number!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new MikuException("Please provide a valid task number!");
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeString) throws MikuException {
        final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(dateTimeString, dateTimeFormat);
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

        Matcher matcher = DEADLINE_BY.matcher(arguments);
        if (!matcher.find()) {
            throw new MikuException("Please specify the deadline using /by <time>!");
        }

        String description = arguments.substring(0, matcher.start()).trim();
        if (description.isEmpty()) {
            throw new MikuException("The description of a deadline cannot be empty!");
        }
        String by = arguments.substring(matcher.end()).trim();
        if (by.isEmpty()) {
            throw new MikuException("The deadline time cannot be empty!");
        }
        LocalDateTime byDateTime = parseDateTime(by);
        return new Deadline(description, byDateTime);
    }

    private static Event parseEvent(String arguments) throws MikuException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("The description cannot be empty!");
        }

        Matcher fromMatcher = EVENT_FROM.matcher(arguments);
        if (!fromMatcher.find()) {
            throw new MikuException("Please specify the start time using /from <time>!");
        }

        Matcher toMatcher = EVENT_TO.matcher(arguments);
        if (!toMatcher.find(fromMatcher.end())) {
            throw new MikuException("Please specify the end time using /to <time>!");
        }

        String description = arguments.substring(0, fromMatcher.start()).trim();
        if (description.isEmpty()) {
            throw new MikuException("The description of an event cannot be empty!");
        }
        String from = arguments.substring(fromMatcher.end(), toMatcher.start()).trim();
        if (from.isEmpty()) {
            throw new MikuException("The start time cannot be empty!");
        }
        String to = arguments.substring(toMatcher.end()).trim();
        if (to.isEmpty()) {
            throw new MikuException("The end time cannot be empty!");
        }
        LocalDateTime fromDateTime = parseDateTime(from);
        LocalDateTime toDateTime = parseDateTime(to);
        return new Event(description, fromDateTime, toDateTime);
    }

    private static String parseKeyword(String arguments) throws MikuException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("Please specify a keyword to search for!");
        }
        return arguments.trim();
    }
}
