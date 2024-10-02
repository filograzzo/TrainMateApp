package DomainModel;

import java.sql.Date;
import java.sql.Time;

public class Course {
    int id;
    private String name;
    private String bodyPartsTrained;
    private int maxParticipants;

    Time time;
    String day;
    private int participants;

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

    private int trainerID;
    public Course(int id,String name, int maxParticipants, int trainerID, String bodyPartsTrained,Time time,String day) {
        this.id = id;
        this.name = name;
        this.maxParticipants = maxParticipants;
        this.trainerID = trainerID;
        this.bodyPartsTrained = bodyPartsTrained;
        this.time=time;
        this.day=day;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getMaxParticipants() {
        return maxParticipants;
    }
    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getParticipants() {
        return participants;
    }
    public int getIDTrainer() {
        return trainerID;
    }
    public String getBodyPartsTrained() {
        return bodyPartsTrained;
    }
    public Time getTime() {
        return time;
    }
    public String getDay() {
        return day;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBodyPartsTrained(String bodyPartsTrained) {
        this.bodyPartsTrained = bodyPartsTrained;
    }
    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTime(Time time) {
        this.time = time;
    }
    public void setDay(String day) {
        this.day = day;
    }


}
