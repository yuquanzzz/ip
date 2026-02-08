package miku.ui;

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
public class GuiMainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Miku miku;

    private final Image userImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/images/DaUser.png")));
    private final Image mikuImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/images/DaDuke.png")));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Miku instance and renders the welcome message. */
    public void setMiku(Miku miku) {
        this.miku = miku;
        dialogContainer.getChildren().add(
                GuiDialogBox.getMikuDialog(miku.getWelcomeMessage(), mikuImage)
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
        dialogContainer.getChildren().addAll(
                GuiDialogBox.getUserDialog(input, userImage),
                GuiDialogBox.getMikuDialog(response, mikuImage)
        );
        userInput.clear();

        if (miku.isExit()) {
            sendButton.setDisable(true);
            userInput.setDisable(true);
            PauseTransition delay = new PauseTransition(Duration.seconds(2.0));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
