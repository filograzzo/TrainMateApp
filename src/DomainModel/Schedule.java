package DomainModel;

import java.util.ArrayList;
import java.util.List;

//
public class Schedule {
    private int id;
    private String name;
    //tolta la lista di trainings perché è possibile vedere in quali allenamenti è stata usata questa scheda semplicemente con una query sulla foreign key che c'è in training.
    private List<ExerciseDetail> exerciseDetails = new ArrayList<>();

    public Schedule (int id, String name){
        this.id = id;
        this.name = name;
    }

    public Schedule(int id, String name, List<ExerciseDetail> exercises) {
        this.id = id;
        this.name = name;
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
