package game;

import org.pmw.tinylog.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private List<String> solution;
    private List<List<String>> field;
    private int currentX;
    private int currentY;
    private String state;
    private int stateCounter;
    boolean won;
    boolean lost;
    String playerName;

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

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public boolean getWon() { return won; }

    public boolean getLost() { return lost; }

    public String getPlayerName() { return playerName; }

    public String getState() { return state; }

    public List<String> getSolution() { return solution; }

    public int getStateCounter() { return stateCounter; }

    public void setPlayerName(String name) {
        this.playerName = name;
    }


    public String getFieldXY(int x, int y) {
        return this.field.get(y).get(x);
    }

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
        if (stateCounter == 10) {
            won = true;
            Logger.info("You completed the puzzle!");
        }
    }

    public void setLoseCondition() {
        this.lost = true;
    }

}
