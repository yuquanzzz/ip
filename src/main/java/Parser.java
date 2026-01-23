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
            if (index < 0) {
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
        return new Deadline(description, by);
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
        DELETE,
        UNKNOWN
    }
}
