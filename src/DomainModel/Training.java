package DomainModel;

import java.sql.Date;
import java.sql.Timestamp;

//allnamento cliente
public class Training {
    private int id;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;
    private String note;
    private int scheduleId;
    private String username;

    public Training(int id, Date date, Timestamp startTime, Timestamp endTime, String note, int scheduleId, String username) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.note = note;
        this.scheduleId = scheduleId;
        this.username = username;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getSchedule() {
        return scheduleId;
    }

    public void setSchedule(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
