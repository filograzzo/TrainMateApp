package DomainModel;

import java.sql.Timestamp;

public class Schedule {
    //#TODO:giorno,id esercizi
    private int id;
    private String activity;//togliere
    private Timestamp startTime;//#TODO:spostare nel training
    private Timestamp endTime;//spostare in training

    public Schedule(int id, String activity, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
