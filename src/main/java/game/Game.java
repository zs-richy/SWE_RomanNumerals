package game;

import org.pmw.tinylog.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private List<String> solution;
    private List<List<String>> field;

    public Game() {
        solution = initSolution();
        this.field = initField();
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

    public String getFieldXY(int x, int y) {
        return this.field.get(x).get(y);
    }

}
