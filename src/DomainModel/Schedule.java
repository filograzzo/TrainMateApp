package DomainModel;

import java.util.ArrayList;
import java.util.List;

//
public class Schedule {
    private int id;
    private String name;
    private String customerUsername;
    private List<ExerciseDetail> exerciseDetails = new ArrayList<>();

    public Schedule (int id, String name, String customer){
        this.id = id;
        this.name = name;
        this.customerUsername = customer;
    }

    public Schedule(int id, String name, String customer, List<ExerciseDetail> exercises) {
        this.id = id;
        this.name = name;
        this.customerUsername = customer;
        this.exerciseDetails = exercises;
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

    public String getCustomer() {
        return customerUsername;
    }

    public void setCustomer(String customer) {
        this.customerUsername = customer;
    }

    public void setExercises(List<ExerciseDetail> exercises) {
        this.exerciseDetails = exercises;
    }
    public List<ExerciseDetail> getExercises() {
        return exerciseDetails;
    }
    public void addExercise(ExerciseDetail exercise) {
        exerciseDetails.add(exercise);
    }
    public void removeExercise(ExerciseDetail exercise) {
        exerciseDetails.remove(exercise);
    }
}