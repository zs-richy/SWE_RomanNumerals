package game;

import java.time.format.DateTimeFormatter;

public class Result implements Comparable<Result> {
    private String name;
    private int state;
    private double time;
    private String date;

    public Result() {

    }

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

    @Override
    public int compareTo(Result other) {
        final int stateComparison = Integer.compare(state, other.getState());
        if (stateComparison != 0) {
            return stateComparison;
        }
        return Double.compare(other.getTime(), time);
    }
}
