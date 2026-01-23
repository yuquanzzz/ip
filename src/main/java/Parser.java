public class Parser {

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
            default -> Command.UNKNOWN;
        };
    }

    public static String[] parseInput(String input) {
        return input.split(" ", 2);
    }

    public static int parseTaskIndex(String indexString) {
        return Integer.parseInt(indexString) - 1;
    }

    public static Task parseTask(Command command, String arguments) {
        return switch (command) {
            case TODO -> new Todo(arguments);
            case DEADLINE -> parseDeadline(arguments);
            case EVENT -> parseEvent(arguments);
            default -> null;
        };
    }

    private static Deadline parseDeadline(String arguments) {
        int byIndex = arguments.indexOf("/by ");
        String description = arguments.substring(0, byIndex - 1);
        String by = arguments.substring(byIndex + 4);
        return new Deadline(description, by);
    }

    private static Event parseEvent(String arguments) {
        int fromIndex = arguments.indexOf("/from ");
        int toIndex = arguments.indexOf(" /to ");
        String description = arguments.substring(0, fromIndex - 1);
        String from = arguments.substring(fromIndex + 6, toIndex);
        String to = arguments.substring(toIndex + 5);
        return new Event(description, from, to);
    }

    enum Command {
        LIST,
        BYE,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        UNKNOWN
    }
}
