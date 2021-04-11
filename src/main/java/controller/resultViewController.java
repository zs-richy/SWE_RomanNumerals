package controller;

import game.Result;
import game.resultContainer;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class resultViewController {

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

    @FXML
    public void initialize() {
        ArrayList<Result> results = resultContainer.getResults();
        //nameColumn
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //allegianceColumn
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        //positionColumn
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        //salaryColumn
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        resultTable.getItems().addAll(results);

    }

}
