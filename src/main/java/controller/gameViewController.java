package controller;

import game.Direction;
import game.Game;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.pmw.tinylog.Logger;
import java.util.ArrayList;

public class gameViewController {
    Game game;
    ArrayList<ArrayList<Label>> labels;
    int currentX = 3;
    int currentY = 3;

    @FXML
    GridPane gridPane;
    @FXML
    AnchorPane mainPane;


    public String getColor(int x, int y) {
        String fieldXY = game.getFieldXY(x,y);
        String color = "black";
        switch (fieldXY) {
            case "I": color = "yellow"; break;
            case "V": color = "lawngreen"; break;
            case "X": color = "red"; break;
            case "L": color = "cyan"; break;
            case "": color = "gray"; break;
        }

        return color;
    }

    public void preMove() {
        Label currentLabel = labels.get(game.getCurrentX()).get(game.getCurrentY());
        currentLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public void postMove() {
        labels.get(game.getCurrentX()).get(game.getCurrentY()).setBorder(new Border(new BorderStroke(Color.DARKBLUE,
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(4, 4, 4, 4))));
    }

    @FXML
    public void gridPaneKeyPressed(KeyEvent e) {
        Logger.info("Key pressed on gridPane.");
        preMove();
        if (e.getCode() == KeyCode.UP) {
            Logger.info("UP key");
            game.move(Direction.UP);
        }
        if (e.getCode() == KeyCode.DOWN) {
            Logger.info("DOWN key");
            game.move(Direction.DOWN);
        }
        if (e.getCode() == KeyCode.LEFT) {
            Logger.info("LEFT key");
            game.move(Direction.LEFT);
        }
        if (e.getCode() == KeyCode.RIGHT) {
            Logger.info("RIGHT key");
            game.move(Direction.RIGHT);
        }
        postMove();
    }

    @FXML
    public void initialize() {
        gridPane.setFocusTraversable(true);
        labels = new ArrayList<ArrayList<Label>>();
        gridPane.setHgap(0.25);
        gridPane.setVgap(0.25);
        game = new Game();


        for (int i = 0; i < 7; i++) {
            ArrayList<Label> iterateLabel = new ArrayList<Label>();
            for (int j = 0; j < 7; j++) {
                Label gridLabel = new Label();
                gridLabel.setPrefWidth(30);
                gridLabel.setPrefHeight(30);
                gridLabel.setAlignment(Pos.CENTER);
                gridLabel.setText(game.getFieldXY(i,j));
                gridLabel.setFont((new Font(15)));
                gridLabel.setStyle("-fx-font-weight: bold; -fx-background-color: " + getColor(i,j) + ";");
                gridLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                gridPane.add((Node) gridLabel, i,j);
                iterateLabel.add(gridLabel);
            }
            labels.add(iterateLabel);
        }

        labels.get(game.getCurrentX()).get(game.getCurrentY()).setBorder(new Border(new BorderStroke(Color.DARKBLUE,
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(4, 4, 4, 4))));

    }

}
