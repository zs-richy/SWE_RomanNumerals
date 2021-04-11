package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class resultContainer {
    private static ArrayList<Result>  results = new ArrayList<>();

    public static void addResult(Result result)  {
        results.add(result);
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File("asd.json");
        try {
            om.writeValue(file, results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void orderResults() {
        Collections.sort(results, Collections.reverseOrder());
        System.out.println(results);
    }

    public static ArrayList<Result> getResults() {
        return results;
    }

}
