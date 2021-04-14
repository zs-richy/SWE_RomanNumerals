package controller;

import game.Direction;
import game.Game;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;

import javax.swing.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    @FXML
    AnchorPane paneGame;
    @FXML
    AnchorPane paneResult;
    @FXML
    AnchorPane paneStart;
    @FXML
    Button startButton;
    @FXML
    Button mainMenuButton;
    @FXML
    TextField nameField;
    @FXML
    Label resultLabel;
    @FXML
    Label currentMoveLabel;
    @FXML
    Label nextGoalLabel;
    @FXML
    Label timeLabel;
    Timeline timeline;

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

        currentMoveLabel.setText("Current move: " + game.getState());
        nextGoalLabel.setText("Next goal: " + game.getSolution().get(game.getStateCounter()));

        if (game.isWon()) {
            updateViewWon();
        } else if(game.isLost()) {
            updateViewLost();
        }
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

    public void updateViewWon() {
        paneGame.setDisable(true);
        resultLabel.setText("Congrats  " + game.getPlayerName() + "!\nYou completed the puzzle!");
        resultLabel.setTextFill(Color.GREEN);
        resultLabel.setAlignment(Pos.CENTER);
        paneGame.setOpacity(0.1);
        paneResult.setVisible(true);
    }

    public void updateViewLost() {
        game.setEndTimer();
        timeline.stop();
        paneGame.setDisable(true);
        resultLabel.setText("You failed " + game.getPlayerName());
        resultLabel.setTextFill(Color.RED);
        resultLabel.setAlignment(Pos.CENTER);
        paneGame.setOpacity(0.1);
        paneResult.setVisible(true);
    }

    public void loadMainMenu(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void startButtonClicked() {
        if (nameField.getText().equals("")) {
            game.setPlayerName("Anonymous");
        } else {
            game.setPlayerName(nameField.getText());
        }
        game.setStartTimer();
        paneGame.setVisible(true);
        paneStart.setVisible(false);
    }

    public void backButtonClicked(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
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
                gridLabel.setPrefWidth(40);
                gridLabel.setPrefHeight(40);
                gridLabel.setAlignment(Pos.CENTER);
                gridLabel.setText(game.getFieldXY(i,j));
                gridLabel.setFont((new Font(20)));
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
        nextGoalLabel.setText("Next goal: " + game.getSolution().get(game.getStateCounter()));

        DateFormat timeFormat = new SimpleDateFormat( "mm:ss.S" );
        timeline =  new Timeline(
                new KeyFrame(
                        Duration.millis(10),
                        event -> {
                            final double diff =  System.currentTimeMillis() - game.getStartTime();
                            timeLabel.setText( timeFormat.format( diff ) );
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }


}
