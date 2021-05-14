package result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.tinylog.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class that holds the result objects and handles persistence.
 */
public class resultContainer {
    private static ArrayList<Result>  results = new ArrayList<>();

    /**
     * Adds a result to the existing results.
     * @param result the result to be added to the container
     */
    public static void addResult(Result result)  {
        results.add(result);
        orderResults();
        writeResults();
        Logger.debug("Result added to container.");
    }

    /**
     * Orders the results in the container.
     */
    public static void orderResults() {
        Collections.sort(results, Collections.reverseOrder());
    }

    /**
     * Returns the results.
     * @return results contained in the {@code static ArrayList}
     */
    public static ArrayList<Result> getResults() {
        return results;
    }

    /**
     * Writes the results to a JSON file.
     */
    public static void writeResults() {
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            //FileWriter writer = new FileWriter("results.json");
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File("results.json")), StandardCharsets.UTF_8);
            om.writeValue(writer, results);
            writer.close();
            Logger.info("Game results written to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the results from a JSON file.
     */
    public static void readResults() throws FileNotFoundException {
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        File storageFile = new File("results.json");
        if(storageFile.exists()) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(new File("results.json")), StandardCharsets.UTF_8);
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                Result[] res = om.readValue(reader, Result[].class);
                results = new ArrayList<>(Arrays.asList(res));
                Logger.info("Game results read from file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            orderResults();
        }
    }

}
