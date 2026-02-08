package miku.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import miku.Miku;

/**
 * JavaFX Application entry point for the Miku GUI.
 */
public class GuiApplication extends Application {

    private final Miku miku = new Miku(Miku.getDefaultStorageDir());

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            fxmlLoader.<GuiMainWindow>getController().setMiku(miku);
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load GUI", e);
        }
    }
}
