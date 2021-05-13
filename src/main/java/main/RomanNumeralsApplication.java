package main;

import result.resultContainer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

// CHECKSTYLE:OFF
public class RomanNumeralsApplication extends Application {

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
