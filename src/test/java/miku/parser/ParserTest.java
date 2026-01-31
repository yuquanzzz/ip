package miku.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import miku.command.AddCommand;
import miku.command.ByeCommand;
import miku.command.Command;
import miku.command.ListCommand;
import miku.exception.MikuException;

class ParserTest {

    @Test
    void parse_listCommand_returnsListCommand() throws MikuException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parse_byeCommand_returnsByeCommand() throws MikuException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ByeCommand.class, command);
        assertTrue(command.isExit());
    }

    @ParameterizedTest
    @CsvSource({
        "mark 1, miku.command.MarkCommand",
        "unmark 1, miku.command.UnmarkCommand",
        "delete 1, miku.command.DeleteCommand"
    })
    void parse_indexBasedCommandWithValidIndex_returnsCorrectCommand(String input, String expectedClass)
            throws MikuException {
        Command command = Parser.parse(input);
        assertEquals(expectedClass, command.getClass().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "todo read book",
        "deadline submit report /by 2026-12-31 23:59",
        "event meeting /from 2026-08-15 10:00 /to 2026-08-15 12:00"
    })
    void parse_addCommands_returnsAddCommand(String input) throws MikuException {
        Command command = Parser.parse(input);
        assertInstanceOf(AddCommand.class, command);
    }

    @ParameterizedTest
    @ValueSource(strings = {"list", "   list", "list   ", "   list   "})
    void parse_listCommandWithExtraWhitespace_returnsListCommand(String input) throws MikuException {
        Command command = Parser.parse(input);
        assertInstanceOf(ListCommand.class, command);
    }

    @ParameterizedTest
    @ValueSource(strings = {"todo read book", "todo   read book   ", "   todo   read book"})
    void parse_todoWithExtraWhitespace_returnsAddCommand(String input) throws MikuException {
        Command command = Parser.parse(input);
        assertInstanceOf(AddCommand.class, command);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "deadline submit report /by 2026-12-31 23:59",
        "deadline submit report   /by   2026-12-31 23:59",
        "   deadline submit report   /by   2026-12-31 23:59   "
    })
    void parse_deadlineWithExtraWhitespace_returnsAddCommand(String input) throws MikuException {
        Command command = Parser.parse(input);
        assertInstanceOf(AddCommand.class, command);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "event meeting /from 2026-08-15 10:00 /to 2026-08-15 12:00",
        "event meeting   /from   2026-08-15 10:00   /to   2026-08-15 12:00",
        "   event meeting   /from   2026-08-15 10:00   /to   2026-08-15 12:00   "
    })
    void parse_eventWithExtraWhitespace_returnsAddCommand(String input) throws MikuException {
        Command command = Parser.parse(input);
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void parse_emptyInput_exceptionThrown() {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse("   "));
        assertEquals("Invalid command, please try again with a valid command.", exception.getMessage());
    }

    @Test
    void parse_invalidCommand_exceptionThrown() {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse("invalid"));
        assertEquals("Invalid command, please try again with a valid command.", exception.getMessage());
    }

    @Test
    void parse_todoWithoutDescription_exceptionThrown() {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse("todo"));
        assertEquals("The description cannot be empty!", exception.getMessage());
    }

    @Test
    void parse_todoWithOnlyWhitespace_exceptionThrown() {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse("todo   "));
        assertEquals("The description cannot be empty!", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
        "deadline submit report, Please specify the deadline using /by <time>!",
        "deadline /by 2026-12-31 23:59, The description of a deadline cannot be empty!",
        "'deadline submit report /by ', The deadline time cannot be empty!",
        "deadline submit report /by 31-12-2026, The time cannot be parsed! Use the format yyyy-MM-dd HH:mm"
    })
    void parse_deadlineWithInvalidFormat_exceptionThrown(String input, String expectedMessage) {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse(input));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
        "event meeting /to 2026-08-15 12:00, Please specify the start time using /from <time>!",
        "event meeting /from 2026-08-15 10:00, Please specify the end time using /to <time>!",
        "event /from 2026-08-15 10:00 /to 2026-08-15 12:00, The description of an event cannot be empty!",
        "event meeting /from  /to 2026-08-15 12:00, The start time cannot be empty!",
        "'event meeting /from 2026-08-15 10:00 /to ', The end time cannot be empty!"
    })
    void parse_eventWithInvalidFormat_exceptionThrown(String input, String expectedMessage) {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse(input));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
        "mark, Please specify which task to mark!",
        "unmark, Please specify which task to unmark!",
        "delete, Please specify which task to delete!"
    })
    void parse_indexBasedCommandWithoutIndex_exceptionThrown(String input, String expectedMessage) {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse(input));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"mark abc", "unmark abc", "delete abc"})
    void parse_indexBasedCommandWithNonNumericIndex_exceptionThrown(String input) {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse(input));
        assertEquals("Please provide a valid task number!", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"mark -1", "unmark -1", "delete -1"})
    void parse_indexBasedCommandWithNegativeNumber_exceptionThrown(String input) {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse(input));
        assertEquals("Task number must be a positive number!", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"mark 0", "unmark 0", "delete 0"})
    void parse_indexBasedCommandWithZero_exceptionThrown(String input) {
        MikuException exception = assertThrows(MikuException.class, () -> Parser.parse(input));
        assertEquals("Task number must be a positive number!", exception.getMessage());
    }
}
