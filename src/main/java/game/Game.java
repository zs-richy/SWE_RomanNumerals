package game;

import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.util.Arrays;
import java.util.List;

import static game.Progress.*;

/**
 * Class that contains the game logic.
 */
@Getter
@Setter
public class Game {

    private List<String> solution;
    private List<List<String>> field;
    private int currentX;
    private int currentY;
    private String state;
    private int stateCounter;
    private Progress gameProgress;
    private String playerName;
    private double startTime;
    private double endTime;
    private Result result;

    /**
     * Creates a {@code Game} object.
     */
    public Game() {
        solution = initSolution();
        this.field = initField();
        currentX = 3;
        currentY = 3;
        state = "";
        stateCounter = 0;
        gameProgress = PLAYING;
        startTime = 0;
        Logger.info("Game object initialized.");
    }

    /**
     * Handles the movement in the given direction.
     * Moves in the give direction if possible and updates the game state
     * accordingly with {@code updateState()} method.
     * Also starts the timer by calling {@code setStartTimer()} on the
     * first move.
     * @param direction the direction of the movement
     */
    public void move(Direction direction) {
        if(this.startTime == 0) {
            this.setStartTimer();
        }
        if (canMove(direction)) {
            switch (direction) {
                case UP -> currentY--;
                case DOWN -> currentY++;
                case LEFT -> currentX--;
                case RIGHT -> currentX++;
            }
            updateState();
        } else {
            Logger.info("Can't move the given direction.");
        }
    }


    /**
     * Checks if it is possible to move in the given direction.
     *
     * @param direction the direction of the movement
     * @return {@code true} if possible to move in the specified direction;
     *         {@code false} otherwise
     */
    public boolean canMove(Direction direction) {
        boolean canMove = false;

        switch(direction) {
            case DOWN -> {
                if (currentY < 6) {
                    canMove = true;
                }
            }
            case UP -> {
                if (currentY > 0) {
                    canMove = true;
                }
            }
            case RIGHT -> {
                if (currentX < 6) {
                    canMove = true;
                }
            }
            case LEFT -> {
                if (currentX > 0) {
                    canMove = true;
                }
            }
        }

        return canMove;

    }

    /**
     * Updates the game state based on the current coordinates.
     * Concatenates the current field's value to previous state
     * if it's not an empty field. If the field is empty checks
     * for win/lose condition.
     */
    public void updateState() {
        if (!getFieldByCoord(currentX, currentY).equals("")) {
            state = state + getFieldByCoord(currentX, currentY);
        } else {
            if (isCorrectState()) {
                stateCounter++;
                Logger.info("Found the next component of the solution!");
                updateWinCondition();
            } else {
                updateLoseCondition();
                Logger.info("You FAILED!");
            }
            state = "";
        }
        Logger.info("State updated :" + state);
    }

    /**
     * Checks if the current state corresponds to the puzzle solution.
     *
     * @return {@code true} if the current state is the next component
     * of the solution; {@code false} otherwise
     */
    public boolean isCorrectState() {
        boolean correct = false;

        if (state.equals(solution.get(stateCounter))) {
            correct = true;
        }

        return correct;
    }

    /**
     * Ends the game if the player has completed the puzzle.
     * Ends the game and calls {@code calculateResult()} method
     * to calculate the game results.
     */
    public void updateWinCondition() {
        if (stateCounter == 40) {
            gameProgress = WON;
            calculateResult();
            Logger.info("You completed the puzzle!");
        }
    }

    /**
     * Ends the game if the player failed to complete the puzzle.
     * Ends the game and calls {@code calculateResult()} method
     * to calculate the game results.
     */
    public void updateLoseCondition() {
        gameProgress = LOST;
        calculateResult();
    }

    /**
     * Calculates the game results.
     */
    private void calculateResult() {
        this.setEndTimer();
        double completionTime = (endTime-startTime) / 1000;
        result = new Result(playerName, stateCounter, completionTime);

        Logger.info("Game result: " + result.toString());
    }

    /**
     * Checks whether the player has completed the puzzle.
     * @return {@code true} if the puzzle is completed;
     * {@code false} otherwise
     */
    public boolean isWon() {
        if (gameProgress == WON) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the player has failed the puzzle.
     * @return {@code true} if the player failed;
     * {@code false} otherwise
     */
    public boolean isLost() {
        if (gameProgress == LOST) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Initialize the puzzle solution.
     *
     * @return a list with the solution
     */
    private List<String> initSolution() {
        List<String> solution;
        solution = Arrays.asList("I","II","III","IV","V","VI","VII","VIII","IX","X",
                "XI","XII","XIII","XIV","XV","XVI","XVII","XVIII","XIX","XX",
                "XXI","XXII","XXIII","XXIV","XXV","XXVI","XXVII", "XXVIII","XXIX","XXX",
                "XXXI","XXXII","XXXIII","XXXIV","XXXV","XXXVI","XXXVII","XXXVIII","XXXIX","XL");

        return solution;
    }

    /**
     * Initialize the game board.
     *
     * @return a list with the fields on the board
     */
    private List<List<String>> initField() {
        List<List<String>> field;
        field = Arrays.asList(
                Arrays.asList("I", "I", "X", "V", "", "L", "X"),
                Arrays.asList("X", "L", "", "I", "X", "V", "I"),
                Arrays.asList("", "I", "V", "I", "X", "", "X"),
                Arrays.asList("L", "I", "X", "", "V", "I", "X"),
                Arrays.asList("X", "", "X", "I", "X", "I", ""),
                Arrays.asList("L", "I", "V", "L", "", "X", "X"),
                Arrays.asList("V", "I", "", "X", "I", "X", "L")
        );

        return field;
    }

    /**
     * Returns the specified field of the game board.
     * The origin of indexes is the top-left corner!
     *
     * @param x row of the element to get
     * @param y column of the element to get
     * @return the field corresponding to (x,y) coordinate
     */
    public String getFieldByCoord(int x, int y) {
        return this.field.get(y).get(x);
    }

    /**
     * Sets the start time to current time.
     */
    private void setStartTimer() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Sets the end time to current time.
     */
    private void setEndTimer() {
        this.endTime = System.currentTimeMillis();
    }

}
