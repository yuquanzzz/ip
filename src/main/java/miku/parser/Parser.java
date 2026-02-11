package miku.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
    private static final Pattern RANGE_PATTERN = Pattern.compile("^(\\d+)\\s*-\\s*(\\d+)$");

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
        case "find" -> new FindCommand(parseKeyword(parsedInput.arguments()));
        // task action
        case "mark" -> new MarkCommand(parseTaskIndexes(parsedInput.arguments(), "mark"));
        case "unmark" -> new UnmarkCommand(parseTaskIndexes(parsedInput.arguments(), "unmark"));
        case "delete" -> new DeleteCommand(parseTaskIndexes(parsedInput.arguments(), "delete"));
        // task
        case "todo" -> new AddCommand(parseTodo(parsedInput.arguments()));
        case "deadline" -> new AddCommand(parseDeadline(parsedInput.arguments()));
        case "event" -> new AddCommand(parseEvent(parsedInput.arguments()));
        // invalid command
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

    private static List<Integer> parseTaskIndexes(String indexString, String commandType) throws MikuException {
        // checks if index is empty
        if (indexString == null || indexString.trim().isEmpty()) {
            throw new MikuException("Please specify which task to " + commandType + "!");
        }

        Set<Integer> indexes = new TreeSet<>();
        String[] tokens = indexString.split(",");
        for (String token : tokens) {
            String trimmedToken = token.trim();
            if (trimmedToken.isEmpty()) {
                throw new MikuException("Please provide a valid task number!");
            }

            Matcher rangeMatcher = RANGE_PATTERN.matcher(trimmedToken);
            if (rangeMatcher.matches()) {
                // handle index ranges
                int start = parseIndex(rangeMatcher.group(1));
                int end = parseIndex(rangeMatcher.group(2));
                if (start > end) {
                    throw new MikuException("Invalid range! Please provide a valid task number!");
                }
                for (int i = start; i <= end; i++) {
                    indexes.add(i - 1); // 0-based indexing
                }
            } else {
                // handle single index
                int index = parseIndex(trimmedToken) - 1; // 0-based indexing
                indexes.add(index);
            }
        }
        return new ArrayList<>(indexes);
    }

    private static int parseIndex(String indexString) throws MikuException {
        try {
            int index = Integer.parseInt(indexString.trim());
            if (index <= 0) {
                throw new MikuException("Task number must be a positive number!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new MikuException("Please provide a valid task number!");
        }
    }

    private static String parseKeyword(String arguments) throws MikuException {
        // checks if arguments is empty
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("Please specify a keyword to search for!");
        }
        return arguments.trim();
    }

    private static Task parseTodo(String arguments) throws MikuException {
        // checks if arguments is empty
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("The description cannot be empty!");
        }
        return new Todo(arguments.trim());
    }

    private static LocalDateTime parseDateTime(String dateTimeString) throws MikuException {
        final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(dateTimeString, dateTimeFormat);
        } catch (DateTimeParseException e) {
            throw new MikuException("The time cannot be parsed! Use the format yyyy-MM-dd HH:mm");
        }
    }

    private static Deadline parseDeadline(String arguments) throws MikuException {
        // checks if arguments is empty
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("The description cannot be empty!");
        }

        // find "/by" parameter and throw exception if not found
        Matcher matcher = DEADLINE_BY.matcher(arguments);
        if (!matcher.find()) {
            throw new MikuException("Please specify the deadline using /by <time>!");
        }

        // find description parameter and throw exception if it is empty
        String description = arguments.substring(0, matcher.start()).trim();
        if (description.isEmpty()) {
            throw new MikuException("The description of a deadline cannot be empty!");
        }

        // find end date time parameter and throw exception if it is empty
        String by = arguments.substring(matcher.end()).trim();
        if (by.isEmpty()) {
            throw new MikuException("The deadline time cannot be empty!");
        }
        LocalDateTime byDateTime = parseDateTime(by);
        return new Deadline(description, byDateTime);
    }

    private static Event parseEvent(String arguments) throws MikuException {
        // checks if arguments is empty
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new MikuException("The description cannot be empty!");
        }

        // find "/from" parameter and throw exception if not found
        Matcher fromMatcher = EVENT_FROM.matcher(arguments);
        if (!fromMatcher.find()) {
            throw new MikuException("Please specify the start time using /from <time>!");
        }

        // find "/to" parameter and throw exception if not found
        Matcher toMatcher = EVENT_TO.matcher(arguments);
        if (!toMatcher.find(fromMatcher.end())) {
            throw new MikuException("Please specify the end time using /to <time>!");
        }

        // find description parameter and throw exception if it is empty
        String description = arguments.substring(0, fromMatcher.start()).trim();
        if (description.isEmpty()) {
            throw new MikuException("The description of an event cannot be empty!");
        }

        // find start and end date time and throw exception if it is empty
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
}
