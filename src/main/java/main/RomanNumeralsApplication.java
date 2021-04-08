package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pmw.tinylog.Logger;

public class RomanNumeralsApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml"));
        stage.setTitle("Logic Game");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Application started.");
        Logger.info("Main Menu loaded.");
    }

}
