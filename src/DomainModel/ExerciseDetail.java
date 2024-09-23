package DomainModel;

public class ExerciseDetail {
    private int id;
    private int serie;
    private int reps;
    private int weight;
    private Schedule schedule = new Schedule();
    private Exercise exercise = new Exercise();


    // Costruttore completo
    public ExerciseDetail(int id, int serie, int reps, int weight, Schedule schedule, Exercise exercise) {
        this.id = id;
        this.serie = serie;
        this.reps = reps;
        this.weight = weight;
        this.schedule = schedule;
        this.exercise = exercise;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
