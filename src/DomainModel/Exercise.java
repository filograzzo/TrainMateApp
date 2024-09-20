package DomainModel;
//#TODO:modificare info e aggiungerne altre,alcuni di questi verranno aggiunti alla schedule del cliente
public class Exercise {
    private int id;
    private int serie;
    private int reps;
    private int weight;
    private int scheduleId;
    private String name;
    private String description;

    public Exercise(int id, int serie, int reps, int weight, int scheduleId, String name, String description) {
        this.id = id;
        this.serie = serie;
        this.reps = reps;
        this.weight = weight;
        this.scheduleId = scheduleId;
        this.name = name;
        this.description = description;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getReps() {
        return reps;
    }

    public void setRepd(int reps) {
        this.reps = reps;
    }

    public int getweight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int idSchedule) {
        this.scheduleId = scheduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
