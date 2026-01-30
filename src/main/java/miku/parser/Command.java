package miku.parser;

public enum Command {
    LIST,
    BYE,
    // tasks
    TODO,
    DEADLINE,
    EVENT,
    // task actions
    MARK,
    UNMARK,
    DELETE,
    // unknown command
    UNKNOWN
}
