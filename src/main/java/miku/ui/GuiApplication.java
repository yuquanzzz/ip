package miku.ui;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import miku.Miku;
import miku.ui.components.MainWindow;

/**
 * JavaFX Application entry point for the Miku GUI.
 */
public class GuiApplication extends Application {

    public static final String APP_NAME = "Miku";
    public static final String BOT_ICON = "/images/Miku.jpg";
    private final Miku miku = new Miku(Miku.getDefaultStorageDir());

    @Override
    public void start(Stage stage) {
        try {
            // set min height and width for windows
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            // set icon and title
            Image icon = new Image(Objects.requireNonNull(
                    this.getClass().getResourceAsStream(BOT_ICON)));
            stage.getIcons().add(icon);
            stage.setTitle(APP_NAME);

            FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMiku(miku);
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load GUI", e);
        }
    }
}
