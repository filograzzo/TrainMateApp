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

    public Schedule(int id, String name, List<Training> trainings, List<ExerciseDetail> exercises) {
        this.id = id;
        this.name = name;
        this.trainings = trainings;
        this.exercises = exercises;
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


    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
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


    public void setExercises(List<ExerciseDetail> exercises) {
        this.exercises = exercises;
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
