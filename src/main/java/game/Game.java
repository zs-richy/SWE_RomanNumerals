package game;

import lombok.Getter;
import lombok.Setter;
import org.pmw.tinylog.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Game {

    private List<String> solution;
    private List<List<String>> field;
    private int currentX;
    private int currentY;
    private String state;
    private int stateCounter;
    private boolean won;
    private boolean lost;
    private String playerName;
    private double startTime;
    private double endTime;
    private Result result;

    public Game() {
        solution = initSolution();
        this.field = initField();
        currentX = 3;
        currentY = 3;
        state = "";
        stateCounter = 0;
        won = false;
        lost = false;
        Logger.info("Game object initialized.");
        startTime = 0;
    }

    private List<String> initSolution() {
        List<String> solution;
        solution = Arrays.asList("I","II","III","IV","V","VI","VII","VIII","IX","X",
                "XI","XII","XIII","XIV","XV","XVI","XVII","XVIII","XIX","XX",
                "XXI","XXII","XXIII","XXIV","XXV","XXVI","XXVII", "XXVIII","XXIX","XXX",
                "XXXI","XXXII","XXXIII","XXXIV","XXXV","XXXVI","XXXVII","XXXVIII","XXXIX","XL");

        return solution;
    }

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

    public String getFieldXY(int x, int y) {
        return this.field.get(y).get(x);
    }

    public void setStartTimer() { this.startTime = System.currentTimeMillis(); }

    public void setEndTimer() { this.endTime = System.currentTimeMillis(); }


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

    public void move(Direction direction) {
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

    public void updateState() {
        if (!getFieldXY(currentX, currentY).equals("")) {
            state = state + getFieldXY(currentX, currentY);
        } else {
            if (isCorrectState()) {
               Logger.info("Found the next component of the solution!");
               checkWinCondition();
            } else {
                setLoseCondition();
                Logger.info("You FAILED!");
            }
            state = "";
        }
        Logger.info("State updated :" + state);
    }

    public boolean isCorrectState() {
        boolean correct = false;

        if (state.equals(solution.get(stateCounter))) {
            correct = true;
            stateCounter++;
        }

        return correct;
    }

    public void checkWinCondition() {
        if (stateCounter == 40) {
            won = true;
            calculateResult();
            Logger.info("You completed the puzzle!");
        }
    }


    public void setLoseCondition() {
        this.lost = true;
        calculateResult();
    }

    public void calculateResult() {
        endTime = System.currentTimeMillis();
        double completionTime = (endTime-startTime) / 1000;
        result = new Result(playerName, stateCounter, completionTime);

        Logger.info("Game result: " + result.toString());
    }


}
