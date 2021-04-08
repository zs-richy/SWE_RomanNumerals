package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainMenuController {

    public void loadScene(Stage _stage, String _fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + _fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = _stage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void startPressed(ActionEvent e) throws IOException {
        Stage scene = (Stage) ((Node) e.getSource()).getScene().getWindow();
        loadScene(scene, "gameView");
    }

}
