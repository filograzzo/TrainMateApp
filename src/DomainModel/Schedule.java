package DomainModel;

import java.sql.Timestamp;

public class Schedule {
    private int id;
    private String activity;
    private Timestamp startTime;
    private Timestamp endTime;

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
