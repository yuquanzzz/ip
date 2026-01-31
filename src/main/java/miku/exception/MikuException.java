package miku.exception;

/**
 * Represents an exception specific to the Miku chatbot application.
 * This exception is thrown when an error occurs during command parsing or execution.
 */
public class MikuException extends Exception {
    /**
     * Constructs a MikuException with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public MikuException(String message) {
        super(message);
    }
}
