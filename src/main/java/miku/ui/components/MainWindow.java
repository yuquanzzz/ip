package miku.ui.components;

import java.util.Objects;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import miku.Miku;

/**
 * Controller for the main GUI window.
 */
public class MainWindow extends AnchorPane {
    public static final String BOT_ICON = "/images/Miku.jpg";
    public static final String USER_ICON = "/images/Ado.jpg";
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Miku miku;

    private final Image mikuImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream(BOT_ICON)));
    private final Image userImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream(USER_ICON)));

    /**
     * Initialises the UI by auto-scrolling the dialog container and setting the input prompt.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userInput.setPromptText("Type here...");
    }

    /** Injects the Miku instance and renders the welcome message. */
    public void setMiku(Miku miku) {
        this.miku = miku;
        dialogContainer.getChildren().add(
                DialogBox.getMikuDialog(miku.getWelcomeMessage(), mikuImage, null)
        );
    }

    /**
     * Handles user input, appends dialog boxes, and exits when requested.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }
        String response = miku.getResponse(input);
        String commandType = miku.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMikuDialog(response, mikuImage, commandType)
        );
        userInput.clear();

        if (miku.isExit()) {
            sendButton.setDisable(true);
            userInput.setDisable(true);
            PauseTransition delay = new PauseTransition(Duration.seconds(3.0));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
