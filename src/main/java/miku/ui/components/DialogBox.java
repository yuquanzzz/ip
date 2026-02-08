package miku.ui.components;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Dialog box displaying a message alongside a speaker image.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load dialog box FXML", e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        applyCircularClip();
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getMikuDialog(String text, Image img, String commandType) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }

    private void applyCircularClip() {
        Circle clip = new Circle();
        clip.centerXProperty().bind(displayPicture.fitWidthProperty().divide(2));
        clip.centerYProperty().bind(displayPicture.fitHeightProperty().divide(2));
        clip.radiusProperty().bind(displayPicture.fitWidthProperty().divide(2));
        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    private void changeDialogStyle(String commandType) {
        if (commandType == null) {
            return;
        }
        switch(commandType) {
        case "AddCommand":
            dialog.getStyleClass().add("add-label");
            break;
        case "MarkCommand":
        case "UnmarkCommand":
            dialog.getStyleClass().add("marked-label");
            break;
        case "DeleteCommand":
            dialog.getStyleClass().add("delete-label");
            break;
        case "MikuException":
            dialog.getStyleClass().add("miku-exception-label");
            break;
        default:
            // Do nothing
        }
    }
}
