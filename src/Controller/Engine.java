package Controller;

import BusinessLogic.Service.*;
import BusinessLogic.Service.Customer.BookCourseService;
import BusinessLogic.Service.Customer.ProfileService;
import BusinessLogic.Service.PersonalTrainer.AgendaService;
import BusinessLogic.Service.PersonalTrainer.MachineService;
import BusinessLogic.Service.PersonalTrainer.ProfilePTService;
import DomainModel.*;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Engine {
    private static Engine istance;
    private ServiceFactory sf;
    private Scanner input = new Scanner(System.in);
    private BaseUser user;


    //CORE FUNCTION -------------------------------------------------------
    private Engine() {
        sf =  ServiceFactory.getInstance();
    }

    public static Engine getInstance() {
        if(istance==null)
            istance = new Engine();
        return istance;
    }

    /*Login*/

    public boolean loginUser(String username, String password,String email){
        boolean logged = false;
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        Customer c= baseUserService.loginUser(username,password,email);
        if (c == null) {
            System.err.println("The user you are trying to log in does not exist.");
        } else {
            if(baseUserService.checkCredentialsCustomer(username,password,email)){
                this.user = baseUserService.getCurrentUser();
                System.out.println("The user has been logged in successfully.");
                ProfileService ps = (ProfileService) sf.getService(sf.PROFILE_SERVICE);
                ps.setCustomer((Customer)user);
                logged = true;
                //BookAppointmentService bas = (BookAppointmentService) sf.getService(sf.BOOKAPPOINTMENT_SERVICE);
                //bas.setCurrentUser(user);
                //BookCourseService bcs = (BookCourseService) sf.getService(sf.BOOKCOURSE_SERVICE);
                //bcs.setCurrentUser(user);

            }else{
                System.out.println("Credenziali errate");
            }
        }
        return logged;
    }
    public boolean loginPersonalTrainer(String username, String password,String email){
        boolean logged = false;
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        PersonalTrainer pt= baseUserService.loginPersonalTrainer(username,password,email);
        if (pt == null) {
            System.err.println("The user you are trying to log in does not exist.");
        } else {
            if(baseUserService.checkCredentialsPT(username,password,email)){
                this.user = baseUserService.getCurrentUser();
                System.out.println("The Personal Trainer has been logged in successfully.");
                ProfilePTService pps = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
                pps.setPersonalTrainer((PersonalTrainer)user);
                AgendaService as = (AgendaService) sf.getService(sf.AGENDA_SERVICE);
                as.setPersonalTrainer(user);
                logged = true;
            }else{
                System.out.println("Credenziali errate");
            }
        }
        return logged;
    }
    /*Register*/

    public boolean registerCustomer( String username, String password, String email){
        boolean registered = false;
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        registered= baseUserService.registerUser(username,password,email);
        this.user = baseUserService.getCurrentUser();
        return registered;
    }
    public boolean registerPT(String email, String password, String username,String accessCode){
        boolean registered = false;
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        registered= baseUserService.registerPersonalTrainer(email,password,username,accessCode);
        this.user = baseUserService.getCurrentUser();
        return registered;
    }
    /*Logout*/

    public void logout(){
        user=null;
    }

    public BaseUser getUser() {
        return user;
    }
    /*Personal Trainer*/
    public void modifyUsernamePT(String oldUsername, String username) {
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            if(profilePTService.modifyUsername(oldUsername, username)){
                System.out.println("Username updated successfully.");
            }
            else{
                System.out.println("Username not updated.It may already exist,choose another one.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyPasswordPT(int id, String password, String oldPassword) {
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            if(profilePTService.modifyPassword(id, password,oldPassword)){
                System.out.println("Password updated successfully.");
            }else{
                System.out.println("Password not updated.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyEmailPT(int id, String email) {
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            if(profilePTService.modifyEmail(id, email)){
                System.out.println("Email updated successfully.");
            }else{
                System.out.println("Email not updated.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateUsernameClient(String oldUsername, String newUsername){
        ProfileService profileService = (ProfileService) sf.getService(sf.PROFILE_SERVICE);
        try {
            if(profileService.modifyUsername(oldUsername, newUsername)){
                System.out.println("Username updated successfully.");
            }else{
                System.out.println("Username not updated.It may already exist,choose another one.");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateEmailClient(String username, String email){
        ProfileService profileService = (ProfileService) sf.getService(sf.PROFILE_SERVICE);
        try {
            if(profileService.modifyEmail(username, email)){
                System.out.println("Email updated successfully.");
            }else{
                System.out.println("Email not updated.It may already exist,choose another one.");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePasswordClient(String username, String NewPassword,String OldPassword){
        ProfileService profileService = (ProfileService) sf.getService(sf.PROFILE_SERVICE);
        try {
            if(profileService.modifyPassword(username, NewPassword,OldPassword)){
                System.out.println("Password updated successfully.");
            }else{
                System.out.println("Password not updated.User or OldPassword may be incorrect");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updatePersonalData(int id, float height, float weight, int age, String gender, String goal){
        ProfileService profileService = (ProfileService) sf.getService(sf.PROFILE_SERVICE);
        try {
            if(profileService.updatePersonalData(id, height, weight,age,gender,goal)){
                System.out.println("Personal data added successfully.");
            }else{
                System.out.println("Personal data not added.");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deletePersonalData(int id){
        ProfileService profileService = (ProfileService) sf.getService(sf.PROFILE_SERVICE);
        try {
            if(profileService.deletePersonalData(id)){
                System.out.println("Personal data deleted successfully.");
            }else{
                System.out.println("Personal data not deleted.");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO:AGENDA

    public  ArrayList<Course> viewAvailableCourses(){
        BookCourseService bookCourseService = (BookCourseService) sf.getService(sf.BOOKCOURSE_SERVICE);
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            ArrayList<Course> courses = bookCourseService.viewAvailableCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses available.");
            } else {
                return courses;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Course> viewCoursesToTake(){
        AgendaService agendaService = (AgendaService) sf.getService(sf.AGENDA_SERVICE);
        try {
            List<Course> courses = agendaService.viewCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses to take.");
            } else {
                return courses;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void addorUpdateCourse(Course course){
        AgendaService agendaService = (AgendaService) sf.getService(sf.AGENDA_SERVICE);
        try{
            agendaService.addOrUpdateCourse(course);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void deleteCourse(int id){
        AgendaService agendaService = (AgendaService) sf.getService(sf.AGENDA_SERVICE);
        try{
            agendaService.deleteCourse(id);
        }catch(Exception e){
            e.printStackTrace();}
    }
    public void addAppointment(int id, int customerId, Date day, Time time){
        AgendaService agendaService = (AgendaService) sf.getService(sf.AGENDA_SERVICE);
        try{
            agendaService.addAppointment(id,customerId, (java.sql.Date) day,time);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void removeAppointment(int id){
        AgendaService agendaService = (AgendaService) sf.getService(sf.AGENDA_SERVICE);
        try{
            agendaService.removeAppointment(id);
        }catch(Exception e){
            e.printStackTrace();}
    }
    //CUSTOMER
    public void bookCourse(int courseId){
        BookCourseService bookCourseService = (BookCourseService) sf.getService(sf.BOOKCOURSE_SERVICE);
        try {
            if(bookCourseService.bookCourse(courseId)){
                System.out.println("Course successfully booked.");
            }else{
                System.out.println("Course not booked.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void cancelBooking(int courseId){
        BookCourseService bookCourseService = (BookCourseService) sf.getService(sf.BOOKCOURSE_SERVICE);
        try {
            if(bookCourseService.cancelBooking(courseId)){
                System.out.println("Booking successfully canceled.");
            }else{
                System.out.println("Booking not canceled.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO: BOOK APPOINTMENT, BOOK COURSES

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

    // EXERCISES

    // Solo PT (Personal Trainer)
    public void createExercise(PersonalTrainer personalTrainer) {
        if (personalTrainer.isValid()) {
            ExerciseService exerciseService = (ExerciseService) sf.getService(sf.EXERCISE_SERVICE);
            try {
                // Inserimento dei dati per l'esercizio
                System.out.println("Enter exercise name:");
                String name = input.nextLine();

                boolean ok = false;
                String category = "";
                while (!ok) {
                    // Inserimento e validazione della categoria
                    System.out.println("Enter exercise category (Valid categories: " + Exercise.getValidCategories() + "):");
                    category = input.nextLine();

                    // Verifica se la categoria inserita è valida
                    if (Exercise.getValidCategories().contains(category)) {
                        ok = true;
                    } else {
                        System.out.println("Invalid category. Please enter one of the valid categories.");
                    }
                }

                System.out.println("Enter machine used (or 'None' if no machine):");
                String machine = input.nextLine();

                System.out.println("Enter a description:");
                String description = input.nextLine();

                // Creazione dell'esercizio
                boolean done = exerciseService.createExercise(name, category, machine, description);
                if (!done) {
                    throw new CustomizedException("There has been an error in the creation of the exercise.");
                } else {
                    System.out.println("Exercise successfully created.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Only personal trainers can create exercises.");
        }
    }

    // Solo PT (Personal Trainer)
    public void deleteExercise(PersonalTrainer personalTrainer, Exercise exercise) {
        if (personalTrainer.isValid()) {
            ExerciseService exerciseService = (ExerciseService) sf.getService(sf.EXERCISE_SERVICE);
            try {
                if (exercise == null) {
                    throw new CustomizedException("The exercise does not exist.");
                }

                boolean done = exerciseService.deleteExercise(exercise);
                if (!done) {
                    throw new CustomizedException("There has been an error in the deletion of the exercise.");
                } else {
                    System.out.println("Exercise successfully deleted.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Only personal trainers can delete exercises.");
        }
    }


    public Exercise getExerciseByName() {
        ExerciseService exerciseService = (ExerciseService) sf.getService(sf.EXERCISE_SERVICE);
        try {
            System.out.println("Enter the name of the exercise:");
            String name = input.nextLine();

            Exercise exercise = exerciseService.getExerciseByName(name);
            if (exercise == null) {
                throw new CustomizedException("This name does not refer to any existing exercise.");
            }else return exercise;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CustomizedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Exercise> getAllExercises() {
        ExerciseService exerciseService = (ExerciseService) sf.getService(sf.EXERCISE_SERVICE);
        try {
            // Recupero di tutti gli esercizi
            return exerciseService.getAllExercises();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Exercise> getExercisesByCategory() {
        ExerciseService exerciseService = (ExerciseService) sf.getService(sf.EXERCISE_SERVICE);
        List<Exercise> exercises = new ArrayList<>();
        try {
            // Richiesta della categoria
            System.out.println("Enter exercise category (Valid categories: " + Exercise.getValidCategories() + "):");
            String category = input.nextLine();

            // Validazione della categoria
            if (category == null || category.trim().isEmpty()) {
                throw new IllegalArgumentException("Category must not be null or empty.");
            }

            if (!Exercise.getValidCategories().contains(category)) {
                throw new IllegalArgumentException("Invalid category. Please enter one of the valid categories: " + Exercise.getValidCategories());
            }

            // Recupero degli esercizi per categoria
            exercises = exerciseService.getExercisesByCategory(category);
            return exercises;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //MACHINE

    public void createMachine() {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        try {
            System.out.println("Enter machine name:");
            String name = input.nextLine();

            System.out.println("Enter machine description:");
            String description = input.nextLine();

            boolean state = false;
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Is the machine active? (true/false):");
                String stateInput = input.nextLine();

                if (stateInput.equalsIgnoreCase("true") || stateInput.equalsIgnoreCase("false")) {
                    state = Boolean.parseBoolean(stateInput);
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter 'true' or 'false'.");
                }
            }

            boolean done = machineService.createMachine(name, description, state);
            if (!done) {
                throw new CustomizedException("There has been an error in the creation of the machine.");
            } else {
                System.out.println("Machine successfully created.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CustomizedException e) {
            System.err.println(e.getMessage());
        }
    }


    public void deleteMachine(Machine machine) {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        try {
            if (machine == null) {
                throw new CustomizedException("The machine does not exist.");
            }

            boolean done = machineService.deleteMachine(machine);
            if (!done) {
                throw new CustomizedException("There has been an error in the deletion of the machine.");
            } else {
                System.out.println("Machine successfully deleted.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CustomizedException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateMachine(Machine machine) {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        try {
            if (machine == null) {
                throw new CustomizedException("The machine does not exist.");
            }
            System.out.println("Enter the new name:");
            String newName = input.nextLine();
            machine.setName(newName);

            System.out.println("Enter the new description:");
            String newDescription = input.nextLine();


            boolean newState = false;
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Is the machine active? (true/false):");
                String stateInput = input.nextLine();

                if (stateInput.equalsIgnoreCase("true") || stateInput.equalsIgnoreCase("false")) {
                    newState = Boolean.parseBoolean(stateInput);
                    validInput = true;  // Esce dal ciclo quando l'input è valido
                } else {
                    System.out.println("Invalid input. Please enter 'true' or 'false'.");
                }
            }

            machine.setDescription(newDescription);
            machine.setState(newState);
            boolean done = machineService.updateMachine(machine);
            if (!done) {
                throw new CustomizedException("There has been an error in the update of the machine.");
            } else {
                System.out.println("Machine successfully updated.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CustomizedException e) {
            System.err.println(e.getMessage());
        }
    }


    public List<Machine> getAllMachines() {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        List<Machine> machines;

        try {
            machines = machineService.getAllMachines();
            if (machines.isEmpty()) {
                System.out.println("No machines found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return machines;
    }


    public Machine getMachineByName(String name) {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        Machine machine;

        try {
            machine = machineService.getMachineByName(name);
            if(machine == null) {
                throw new CustomizedException("No machine was found matching the given name.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CustomizedException e) {
            throw new RuntimeException(e);
        }

        return machine;
    }


    public List<Machine> getActiveMachines() {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        List<Machine> activeMachines;

        try {
            activeMachines = machineService.getActiveMachines();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return activeMachines;
    }


    public List<Machine> getInactiveMachines() {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        List<Machine> inactiveMachines;

        try {
            inactiveMachines = machineService.getInactiveMachines();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return inactiveMachines;
    }

    public boolean getMachineState(Machine machine) {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        boolean state = false;

        try {
            if (machine == null) {
                throw new CustomizedException("The machine does not exist.");
            }
            state = machineService.getMachineState(machine);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CustomizedException e) {
            System.err.println(e.getMessage());
        }

        return state;
    }

    public List<Exercise> getExercisesByMachine(Machine machine) {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        List<Exercise> exercises = new ArrayList<>();

        try {
            if (machine == null) {
                throw new CustomizedException("The machine does not exist.");
            }
            exercises = machineService.getExercisesByMachine(machine);
            if (exercises.isEmpty()) {
                System.out.println("No exercises found for this machine.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CustomizedException e) {
            System.err.println(e.getMessage());
        }

        return exercises;
    }




}
