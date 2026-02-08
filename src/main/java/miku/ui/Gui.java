package miku.ui;

/**
 * UI implementation that buffers output for GUI instead of printing to stdout.
 */
public class Gui extends Ui {
    private final StringBuilder buffer = new StringBuilder();

    @Override
    protected void printLine(String message) {
        // buffer messages instead of printing to stdout
        if (!buffer.isEmpty()) {
            buffer.append(System.lineSeparator());
        }
        buffer.append(message);
    }

    @Override
    public void showDividerLine() {
        // Skip console divider lines in GUI responses
    }

    /**
     * Returns the buffered response text collected so far.
     */
    public String getResponse() {
        return buffer.toString();
    }
}
