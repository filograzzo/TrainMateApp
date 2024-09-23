package DomainModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//
public class Schedule {
    private static int counter = 0;
    private int id;
    private String name;
    private List<Training> trainings = new ArrayList<>();
    private List<ExerciseDetail> exercises = new ArrayList<>();

    public Schedule() {
        this.id = ++counter;
    }

    public Schedule(String name) {
        this.id = ++counter; // Incrementa e assegna l'ID univoco
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public List<Training> getTrainings() {
        return trainings;
    }
    public void addTraining(Training training) {
        trainings.add(training);
    }
    public void removeTraining(Training training) {
        trainings.remove(training);
    }


    public List<ExerciseDetail> getExercises() {
        return exercises;
    }
    public void addExercise(ExerciseDetail exercise) {
        exercises.add(exercise);
    }
    public void removeExercise(ExerciseDetail exercise) {
        exercises.remove(exercise);
    }
}
