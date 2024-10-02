package DomainModel;

import java.sql.Date;
import java.sql.Time;

public class Appointment {


    private static int id;
    private int personalTrainerId;
    private int customerId;
    private Date day;
    private Time time;

    public Appointment( int id, int personalTrainerId, int customerId, Date day, Time time) {
        this.id=id;
        this.personalTrainerId = personalTrainerId;
        this.customerId = customerId;
        this.day = day;
        this.time = time;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPersonalTrainerId() {
        return personalTrainerId;
    }

    public void setPersonalTrainerId(int personalTrainerId) {
        this.personalTrainerId = personalTrainerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
