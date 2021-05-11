package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class MainMenuController {

    @FXML
    AnchorPane mainPane;

    public void loadScene(Stage _stage, String _fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + _fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = _stage;
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info(_fxml + " scene loaded.");
    }

    public void startPressed(ActionEvent e) throws IOException {
        Stage scene = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Logger.info("Loading GameView fxml.");
        loadScene(scene, "GameView");
    }

    public void resultsPressed(ActionEvent e) throws IOException {
        Stage scene = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Logger.info("Loading ResultView fxml.");
        loadScene(scene, "ResultView");
    }

    public void exitPressed(ActionEvent e) {
        System.exit(0);
    }

    public void initialize() {
        mainPane.setFocusTraversable(true);
    }

}
