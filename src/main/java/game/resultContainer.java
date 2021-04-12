package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.Main;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class resultContainer {
    private static ArrayList<Result>  results = new ArrayList<>();

    public static void addResult(Result result)  {
        results.add(result);
        orderResults();
        writeResults();
    }

    public static void orderResults() {
        Collections.sort(results, Collections.reverseOrder());
        System.out.println(results);
    }

    public static ArrayList<Result> getResults() {
        return results;
    }

    public static void writeResults() {
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            FileWriter writer = new FileWriter(Paths.get(".").toAbsolutePath().normalize().toString()+"/results/results.json");
            //FileWriter writer = new FileWriter(loader.getResource("results/results.json").getFile());
            om.writeValue(writer, results);
            writer.close();
            Logger.info("Game results written to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readResults() {
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Result[] res = om.readValue(new File(Paths.get(".").toAbsolutePath().normalize().toString()+"/results/results.json"), Result[].class);
            //Result[] res = om.readValue(new FileReader(loader.getResource("results/results.json").getFile()), Result[].class);
            results = new ArrayList<>(Arrays.asList(res));
            Logger.info("Game results read from file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        orderResults();
    }

}
