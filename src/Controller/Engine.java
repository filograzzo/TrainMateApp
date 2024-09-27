package Controller;

import BusinessLogic.Service.ExerciseDetailService;
import BusinessLogic.Service.PersonalTrainer.ProfilePTService;
import BusinessLogic.Service.ScheduleService;
import BusinessLogic.Service.ServiceFactory;
import BusinessLogic.Service.TrainingService;
import DomainModel.BaseUser;
import DomainModel.ExerciseDetail;
import DomainModel.Schedule;
import DomainModel.Training;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Engine {
    private ServiceFactory sf;
    private Scanner input = new Scanner(System.in);


    //CORE FUNCTION -------------------------------------------------------
    private Engine() {
        sf =  ServiceFactory.getInstance();
    }

    /*Personal Trainer*/
    public void modifyUsernamePT(int id, String username) {
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            profilePTService.modifyUsername(id, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyPasswordPT(int id, String password) {
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            profilePTService.modifyPassword(id, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyEmailPT(int id, String email) {
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            profilePTService.modifyEmail(id, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SCHEDULE

    public void createSchedule(BaseUser baseUser){
        if(baseUser.isValid()) {
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try {
                System.out.println("What would you like to call your new schedule?");
                String scheduleName = input.nextLine();

                boolean done = scheduleService.createSchedule(baseUser, scheduleName);
                if (!done) {
                    throw new CustomizedException("There has been an error in the creation of the schedule.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void removeSchedule(BaseUser baseUser, Schedule schedule){
        if(baseUser.isValid()){
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try{
                boolean done = scheduleService.removeSchedule(schedule);
                if(!done)
                    throw new CustomizedException("There has been an error. Your schedule has not been eliminated.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateSchedule(BaseUser baseUser, Schedule schedule){
        if(baseUser.isValid()){
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try{
                System.out.println("What do you want to change the name of the schedule to?");
                String newName = input.nextLine();
                schedule.setName(newName);
                 boolean done = scheduleService.updateSchedule(schedule);
                 if(!done)
                     throw new CustomizedException("The update failed. You did not change the name of your schedule.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Schedule> getSchedules(BaseUser baseUser){
        List <Schedule> schedules = new ArrayList<>();
        if(baseUser.isValid()){
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try{
                schedules = scheduleService.getSchedules(baseUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return schedules;
    }

    //TRAININGS
    public void createTraining(BaseUser baseUser) {
        if (baseUser.isValid()) {
            TrainingService trainingService = (TrainingService) sf.getService(sf.TRAINING_SERVICE);

            try {
                // Formattatore per la data
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                // Richiesta data
                System.out.println("What is the date of the training? (yyyy-MM-dd)");
                String dateInput = input.nextLine();
                LocalDate date = LocalDate.parse(dateInput, dateFormatter);  // Parsing della data

                // Richiesta orario di inizio
                System.out.println("What is the start time of the training? (HH:mm)");
                String startTimeInput = input.nextLine();
                LocalDateTime startTime = date.atTime(LocalDateTime.parse(startTimeInput, timeFormatter).toLocalTime());

                // Richiesta orario di fine
                System.out.println("What is the end time of the training? (HH:mm)");
                String endTimeInput = input.nextLine();
                LocalDateTime endTime = date.atTime(LocalDateTime.parse(endTimeInput, timeFormatter).toLocalTime());

                // Conversione dei tempi in Timestamp
                Timestamp startTimestamp = Timestamp.valueOf(startTime);
                Timestamp endTimestamp = Timestamp.valueOf(endTime);

                // Richiesta note
                System.out.println("Please enter any notes for the training:");
                String note = input.nextLine();

                // Richiesta schedule ID
                System.out.println("Enter the schedule ID:");
                int scheduleID = Integer.parseInt(input.nextLine());

                // Richiesta username
                System.out.println("Enter the username for the training:");
                String username = input.nextLine();

                // Creazione del training
                boolean done = trainingService.createTraining(
                        java.sql.Date.valueOf(date), startTimestamp, endTimestamp, note, scheduleID, username
                );

                if (!done) {
                    throw new CustomizedException("There has been an error in the creation of the training.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                System.err.println(e.getMessage());
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date or time format. Please use the correct format: yyyy-MM-dd for date and HH:mm for time.");
            }
        }
    }

    public void deleteTraining(BaseUser baseUser, Training training) {
        if (baseUser.isValid()) {
            TrainingService trainingService = (TrainingService) sf.getService(sf.TRAINING_SERVICE);
            try {
                boolean done = trainingService.deleteTraining(training);
                if (!done) {
                    throw new CustomizedException("There has been an error in the deletion of the training.");
                } else {
                    System.out.println("Training successfully deleted.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public List<Training> getAllTrainingsByUsername(BaseUser baseUser) {
        if (baseUser.isValid()) {
            TrainingService trainingService = (TrainingService) sf.getService(sf.TRAINING_SERVICE);
            try {
                List<Training> trainings = trainingService.getAllTrainingsByUsername(baseUser);
                if (trainings.isEmpty()) {
                    System.out.println("No trainings found for the user.");
                }
                return trainings;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public List<Training> getAllTrainingsBySchedule(BaseUser baseUser, Schedule schedule) {
        if (baseUser.isValid()) {
            TrainingService trainingService = (TrainingService) sf.getService(sf.TRAINING_SERVICE);
            try {
                List<Training> trainings = trainingService.getAllTraiingsBySchedule(schedule.getId());
                if (trainings.isEmpty()) {
                    System.out.println("No trainings found for the schedule.");
                }
                return trainings;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    //EXERCISEDETAIL

    public void createExerciseDetail(BaseUser baseUser, Schedule schedule) {
        if (baseUser.isValid()) {
            ExerciseDetailService exerciseDetailService = (ExerciseDetailService) sf.getService(sf.EXERCISEDETAIL_SERVICE   );
            try {
                // Richiesta dati per l'ExerciseDetail
                System.out.println("Enter the number of series:");
                int serie = Integer.parseInt(input.nextLine());

                System.out.println("Enter the number of repetitions:");
                int reps = Integer.parseInt(input.nextLine());

                System.out.println("Enter the weight:");
                int weight = Integer.parseInt(input.nextLine());

                int scheduleID = schedule.getId();

                boolean ok = false;
                int exerciseID = 0;
                while(!ok) {
                    System.out.println("Enter the new exercise name:");
                    String exerciseName = input.nextLine();

                    exerciseID = exerciseDetailService.getExerciseIdByName(exerciseName);
                    if (exerciseID == -1) {
                        throw new CustomizedException("The exercise name you entered does not match any existing exercise name.");
                    }else ok = true;
                }
                boolean done = false;
                if(exerciseID == 0)
                    throw new CustomizedException("There has been some problem with the acquisition of the exercise id");
                else {
                    done = exerciseDetailService.createExerciseDetail(serie, reps, weight, scheduleID, exerciseID);
                }

                if (!done) {
                    throw new RuntimeException("There has been an error in the creation of the exercise detail.");
                } else {
                    System.out.println("ExerciseDetail successfully created.");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter valid numbers for serie, reps, weight, scheduleID, and exerciseID.");
            } catch (CustomizedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteExerciseDetail(BaseUser baseUser, ExerciseDetail exerciseDetail) {
        if (baseUser.isValid()) {
            ExerciseDetailService exerciseDetailService = (ExerciseDetailService) sf.getService(sf.EXERCISEDETAIL_SERVICE   );

            try {
                boolean done = exerciseDetailService.removeExerciseDetail(exerciseDetail);
                if (!done) {
                    throw new RuntimeException("There has been an error in the deletion of the exercise detail.");
                } else {
                    System.out.println("ExerciseDetail successfully deleted.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateExerciseDetail(BaseUser baseUser, ExerciseDetail exerciseDetail) {
        if (baseUser.isValid()) {
            ExerciseDetailService exerciseDetailService = (ExerciseDetailService) sf.getService(sf.EXERCISEDETAIL_SERVICE   );
            try {
                System.out.println("Enter the new number of series:");
                int serie = Integer.parseInt(input.nextLine());

                System.out.println("Enter the new number of repetitions:");
                int reps = Integer.parseInt(input.nextLine());

                System.out.println("Enter the new weight:");
                int weight = Integer.parseInt(input.nextLine());

                boolean ok = false;
                int exerciseID = 0;
                while(!ok) {
                    System.out.println("Enter the new exercise name:");
                    String exerciseName = input.nextLine();

                    exerciseID = exerciseDetailService.getExerciseIdByName(exerciseName);
                    if (exerciseID == -1) {
                        throw new CustomizedException("The exercise name you entered does not match any existing exercise name.");
                    }else ok = true;
                }
                boolean done = false;
                if(exerciseID == 0)
                    throw new CustomizedException("There has been some problem with the acquisition of the exercise id");
                else {
                    done = exerciseDetailService.updateExerciseDetail(exerciseDetail.getId(), serie, reps, weight, exerciseID);
                }

                if (!done) {
                    throw new RuntimeException("There has been an error in the update of the exercise detail.");
                } else {
                    System.out.println("ExerciseDetail successfully updated.");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter valid numbers for serie, reps, weight, scheduleID, and exerciseID.");
            } catch (CustomizedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getExerciseDetailById(BaseUser baseUser, int exerciseDetailID) {
        if (baseUser.isValid()) {
            ExerciseDetailService exerciseDetailService = (ExerciseDetailService) sf.getService(sf.EXERCISEDETAIL_SERVICE   );

            try {
                ExerciseDetail exerciseDetail = exerciseDetailService.getExerciseDetailById(exerciseDetailID);

                if (exerciseDetail != null) {
                    System.out.println("ExerciseDetail found: ID = " + exerciseDetail.getId() +
                            ", Series = " + exerciseDetail.getSerie() +
                            ", Reps = " + exerciseDetail.getReps() +
                            ", Weight = " + exerciseDetail.getWeight() +
                            ", Schedule ID = " + exerciseDetail.getSchedule() +
                            ", Exercise ID = " + exerciseDetail.getExercise());
                } else {
                    System.out.println("ExerciseDetail not found.");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number for the ExerciseDetail ID.");
            }
        }
    }




}
