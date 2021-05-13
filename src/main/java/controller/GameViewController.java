package controller;

import game.Direction;
import game.Game;
import result.Result;
import result.resultContainer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import org.tinylog.Logger;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// CHECKSTYLE:OFF
public class GameViewController {
    Game game;
    ArrayList<ArrayList<Label>> labels;
    IntegerProperty currentX = new SimpleIntegerProperty();
    IntegerProperty currentY = new SimpleIntegerProperty();

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
    Label currentLabel;
    private double startTime;
    private double endTime;
    private String playerName;
    private Result result;

    @FXML
    public void gridPaneKeyPressed(KeyEvent e) {
        Logger.info("Key pressed on gridPane");
        preMove();
        if (e.getCode() == KeyCode.UP) {
            Logger.info("UP key pressed");
            game.move(Direction.UP);
        }
        if (e.getCode() == KeyCode.DOWN) {
            Logger.info("DOWN key pressed");
            game.move(Direction.DOWN);
        }
        if (e.getCode() == KeyCode.LEFT) {
            Logger.info("LEFT key pressed");
            game.move(Direction.LEFT);
        }
        if (e.getCode() == KeyCode.RIGHT) {
            Logger.info("RIGHT key pressed");
            game.move(Direction.RIGHT);
        }
        postMove();
    }

    public void preMove() {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        currentLabel = labels.get(game.getCurrentX()).get(game.getCurrentY());
        setBlackBorder(currentLabel);
        timeLabel.setVisible(true);
    }

    public void postMove() {
        currentLabel = labels.get(game.getCurrentX()).get(game.getCurrentY());
        setBlueBorder(currentLabel);
        if (game.isWon()) {
            calculateResult();
            updateViewWon();
        } else if(game.isLost()) {
            calculateResult();
            updateViewLost();
        } else {
            currentMoveLabel.setText("Current move: " + game.getState());
            nextGoalLabel.setText("Next goal: " + game.getSolution().get(game.getStateCounter()));
        }
    }

    public void updateViewWon() {
        paneGame.setDisable(true);
        timeLabel.setVisible(false);
        setBlackBorder(currentLabel);
        resultLabel.setText("Congrats  " + playerName + "!\nYou completed the puzzle in: " +
                result.getTime() + " sec!");
        resultLabel.setTextFill(Color.GREEN);
        resultLabel.setAlignment(Pos.CENTER);
        paneGame.setOpacity(0.1);
        paneResult.setVisible(true);
    }

    public void updateViewLost() {
        currentLabel = labels.get(game.getCurrentX()).get(game.getCurrentY());
        setBlackBorder(currentLabel);
        timeline.stop();
        timeLabel.setVisible(false);
        paneGame.setDisable(true);
        resultLabel.setText("You failed " + playerName + "\nYou reached state " + result.getState() +
                " in " + result.getTime() + " sec!");
        resultLabel.setTextFill(Color.RED);
        resultLabel.setAlignment(Pos.CENTER);
        paneGame.setOpacity(0.1);
        paneResult.setVisible(true);
    }

    public void loadMainMenu(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void startButtonClicked() {
        if (nameField.getText().equals("")) {
            playerName = "Anonymous";
        } else {
            playerName = nameField.getText();
        }
        startTime = 0;
        paneGame.setVisible(true);
        timeLabel.setVisible(false);
        paneGame.setDisable(false);
        paneGame.setOpacity(100);
        paneStart.setVisible(false);
    }

    public void backButtonClicked(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void retryButtonClicked() {
        paneResult.setVisible(false);
        initialize();
        startButtonClicked();
    }

    public String getColor(int x, int y) {
        String fieldXY = game.getFieldByCoord(x,y);
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

    public void setBlackBorder(Label label) {
        label.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public void setBlueBorder(Label label) {
        label.setBorder(new Border(new BorderStroke(Color.DARKBLUE,
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(4, 4, 4, 4))));
    }

    private void calculateResult() {
        endTime = System.currentTimeMillis();
        double completionTime = (endTime-startTime) / 1000;
        result = new Result(playerName, game.getStateCounter(), completionTime);
        resultContainer.addResult(result);
        Logger.info("Game result: " + result.toString());
    }

    private void initGameBoard() {
        labels = new ArrayList<ArrayList<Label>>();
        for (int i = 0; i < 7; i++) {
            ArrayList<Label> iterateLabel = new ArrayList<Label>();
            for (int j = 0; j < 7; j++) {
                Label gridLabel = new Label();
                gridLabel.setPrefWidth(40);
                gridLabel.setPrefHeight(40);
                gridLabel.setAlignment(Pos.CENTER);
                gridLabel.setText(game.getFieldByCoord(i, j));
                gridLabel.setFont((new Font(20)));
                gridLabel.setStyle("-fx-background-color: " + getColor(i, j) + ";" + "-fx-font-weight: bold;");
                gridLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                gridPane.add((Node) gridLabel, i, j);
                iterateLabel.add(gridLabel);
            }
            labels.add(iterateLabel);
        }
    }

    @FXML
    public void initialize() {
        gridPane.setFocusTraversable(true);
        gridPane.setHgap(0.25);
        gridPane.setVgap(0.25);
        game = new Game();

        if (gridPane.getChildren().size() == 0) {
            initGameBoard();
        }

        currentLabel = labels.get(game.getCurrentX()).get(game.getCurrentY());
        setBlueBorder(currentLabel);
        nextGoalLabel.setText("Next goal: " + game.getSolution().get(game.getStateCounter()));

        DateFormat timeFormat = new SimpleDateFormat( "mm:ss.S" );
        timeline =  new Timeline(
                new KeyFrame(
                        Duration.millis(10),
                        event -> {
                            final double diff =  System.currentTimeMillis() - startTime;
                            timeLabel.setText( timeFormat.format( diff ) );
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }

}
// CHECKSTYLE:ON