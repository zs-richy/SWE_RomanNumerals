package game;

import java.time.format.DateTimeFormatter;


/**
 * Class that wraps a game result.
 */
public class Result implements Comparable<Result> {
    private String name;
    private int state;
    private double time;
    private String date;

    /**
     * No args constructor.
     */
    public Result() {

    }

    /**
     * Parametrized constructor.
     * @param name the player name
     * @param state the reached state
     * @param time the elapsed time to reach the given state
     */
    public Result(String name, int state, double time) {
        this.name = name;
        this.state = state;
        this.time = time;
        this.date = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
    }

    public String getName() {
        return this.name;
    }

    public int getState() {
        return this.state;
    }

    public double getTime() {
        return this.time;
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return "Result{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", time=" + time +
                ", date='" + date + '\'' +
                '}';
    }

    /**
     * Compares two Result objects.
     * Comparing is based on the reached state. If the states are equal
     * then compare the elapsed time.
     *
     * @param other the compared Result object
     * @return the order (1: better, 0: equal, -1: worse)
     */
    @Override
    public int compareTo(Result other) {
        final int stateComparison = Integer.compare(state, other.getState());
        if (stateComparison != 0) {
            return stateComparison;
        }
        return Double.compare(other.getTime(), time);
    }
}
