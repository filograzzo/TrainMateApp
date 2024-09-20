package DomainModel;

public class Appointment {


    private static int id;
    private int personalTrainerId;
    private int customerId;
    private String day;
    private String time;

    public Appointment( int id, int personalTrainerId, int customerId, String day, String time) {
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
