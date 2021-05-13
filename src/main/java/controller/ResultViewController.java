package controller;

import org.tinylog.Logger;
import result.Result;
import result.resultContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

// CHECKSTYLE:OFF
public class ResultViewController {

    @FXML
    TableView resultTable;
    @FXML
    TableColumn nameColumn;
    @FXML
    TableColumn stateColumn;
    @FXML
    TableColumn timeColumn;
    @FXML
    TableColumn dateColumn;

    public void handleBackButton(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        Logger.debug("Main menu loaded.");
    }

    @FXML
    public void initialize() {
        ArrayList<Result> results = resultContainer.getResults();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        resultTable.getItems().addAll(results);
    }

}
// CHECKSTYLE:ON