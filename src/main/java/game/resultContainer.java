package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class resultContainer {
    private static ArrayList<Result>  results = new ArrayList<>();

    public static void addResult(Result result) {
        results.add(result);
    }

    public static void orderResults() {
        Collections.sort(results, Collections.reverseOrder());
        System.out.println(results);
    }

    public static ArrayList<Result> getResults() {
        return results;
    }

}
