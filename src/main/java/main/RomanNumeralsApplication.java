package main;

import result.resultContainer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

/**
 * Class that initializes the stage.
 */
public class RomanNumeralsApplication extends Application {

    /**
     * Starts the JavaFX application.
     * @param stage the window of the application
     * @throws Exception I/O exception if the fxml file not found
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        stage.setTitle("Logic Game");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
        resultContainer.readResults();
        Logger.debug("Application started.");
        Logger.debug("Main Menu loaded.");
    }

}
