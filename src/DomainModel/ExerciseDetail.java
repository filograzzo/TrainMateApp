package DomainModel;

public class ExerciseDetail {
    private int id;
    private int serie;
    private int reps;
    private int weight;
    private int scheduleId; //foreign key
    private int exerciseId; //foreign key

    public ExerciseDetail(int id, int serie, int reps, int weight, int scheduleId, int exerciseId) {
        this.id = id;
        this.serie = serie;
        this.reps = reps;
        this.weight = weight;
        this.scheduleId = scheduleId;
        this.exerciseId = exerciseId;
    }

    // Getter e setter per ogni campo
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

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSchedule() {
        return scheduleId;
    }

    public void setSchedule(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getExercise() {
        return exerciseId;
    }

    public void setExercise(int exerciseId) {
        this.exerciseId = exerciseId;
    }
}
