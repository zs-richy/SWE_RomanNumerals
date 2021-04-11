package game;

import java.time.format.DateTimeFormatter;

public class Result {
    private String name;
    private int state;
    private double time;
    private String date;

    public Result(String name, int state, double time) {
        this.name = name;
        this.state = state;
        this.time = time;
        this.date = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
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
}
