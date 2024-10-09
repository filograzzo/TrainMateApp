package Controller;

import BusinessLogic.Service.*;
import BusinessLogic.Service.Customer.BookAppointmentService;
import BusinessLogic.Service.Customer.BookCourseService;
import BusinessLogic.Service.Customer.ProfileService;
import BusinessLogic.Service.PersonalTrainer.AgendaService;
import BusinessLogic.Service.PersonalTrainer.MachineService;
import BusinessLogic.Service.PersonalTrainer.ProfilePTService;
import BusinessLogic.Service.BaseUserService;
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

                NavigationManager navigationManager = NavigationManager.getIstance(null);  // Supponiamo di passare null temporaneamente
                navigationManager.setEngine(this);
                navigationManager.setCurrentUser(this.user);

                ProfileService ps = (ProfileService) sf.getService(sf.PROFILE_SERVICE);
                ps.setCustomer((Customer)user);
                BookAppointmentService bas = (BookAppointmentService) sf.getService(sf.BOOKAPPOINTMENT_SERVICE);
                bas.setCurrentUser(user);
                BookCourseService bcs = (BookCourseService) sf.getService(sf.BOOKCOURSE_SERVICE);
                bcs.setCurrentUser(user);
                logged = true;

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

                NavigationManager navigationManager = NavigationManager.getIstance(null);  // Supponiamo di passare null temporaneamente
                navigationManager.setEngine(this);
                navigationManager.setCurrentUser(this.user);

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
    public String getPTname(int id){
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        return baseUserService.getPTnamebyId(id);
    }
    public String getClientname(int id){
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        return baseUserService.getCustomerNameById(id);
    }
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
    public ArrayList<PersonalTrainer> getAllPersonalTrainers(){
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        ArrayList<PersonalTrainer> personalTrainers = baseUserService.getAllPersonalTrainers();
        if (personalTrainers.isEmpty()) {
            System.out.println("No personal trainers found.");
            return null;
        } else {
            return personalTrainers;
        }
    }
    public boolean checkifFreePT(int id, String day, Time time){
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        if(baseUserService.checkIfFreePT(id,day,time)){
            return true;
        }else{
            return false;
        }
    }
    //TODO:AGENDA
    //COURSES
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
    public ArrayList<Appointment> viewAppointmentsToHave(){
        AgendaService agendaService= (AgendaService) sf.getService(sf.AGENDA_SERVICE);
        try {
            ArrayList<Appointment> appointments = (ArrayList<Appointment>) agendaService.viewAppointments();
            if (appointments.isEmpty()) {
                System.out.println("No appointments found.");
            } else {
                return appointments;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Machine> viewMachinesToTake(){
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        try{
            List<Machine> machines = machineService.getAllMachines();
            if(machines.isEmpty()) {
                System.out.println("No machines to take.");
            }else{return machines;}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return null;
    }


    public void addCourse(String name, int maxParticipants, int trainerID, String bodyPartsTrained,String day, Time time){
        AgendaService agendaService = (AgendaService) sf.getService(sf.AGENDA_SERVICE);
        try{
            if(agendaService.addCourse(name,maxParticipants,trainerID,bodyPartsTrained, day,time)){
                System.out.println("Course successfully added.");}
            else{
                System.out.println("Course not added.There has been a mistake");
            }
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

    public void updateCourse(int id, String name, int maxParticipants, int trainerID, String bodyPartsTrained, String day, Time time){
        AgendaService agendaService = (AgendaService) sf.getService(sf.AGENDA_SERVICE);
        try{
            if(agendaService.updateCourse(id,name,maxParticipants,trainerID,bodyPartsTrained, day,time)){
                System.out.println("Course successfully updated.");
            }else{
                System.out.println("Course not updated.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public String getCoursePTname(int id){
        AgendaService agendaService = (AgendaService) sf.getService(sf.AGENDA_SERVICE);
        try{
            return agendaService.getPTnamebyID(id);
        }catch (SQLException e){
            return null;
        }
    }
    public boolean bookAppointment(int PTid, int customerId, String day, Time time){
        BookAppointmentService bookAppointmentService = (BookAppointmentService) sf.getService(sf.BOOKAPPOINTMENT_SERVICE);
        try{
            if(bookAppointmentService.bookAppointment(customerId,PTid,day,time)){
                System.out.println("Appointment successfully booked.");
                return true;
            }else{
                System.out.println("Appointment not booked.");
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public int getAppointmentIdbyDAYandTime(String day, Time time){
        BookAppointmentService bookAppointmentService = (BookAppointmentService) sf.getService(sf.BOOKAPPOINTMENT_SERVICE);
        try{
            return bookAppointmentService.getAppointmentidByDayandTime(day,time);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    public int getPTidByAppointmentId(int id){
        BookAppointmentService bookAppointmentService = (BookAppointmentService) sf.getService(sf.BOOKAPPOINTMENT_SERVICE);
        try{
            return bookAppointmentService.getPTidByAppointmentId(id);
        }catch(Exception e){
            e.printStackTrace();
        } return 0;
    }

    public ArrayList<Appointment> getAllAppointments(int customerId){
        BookAppointmentService bookAppointmentService = (BookAppointmentService) sf.getService(sf.BOOKAPPOINTMENT_SERVICE);
        try{
            return (ArrayList<Appointment>) bookAppointmentService.viewAppointments(customerId);
        }catch(Exception e){
            e.printStackTrace();}
        return null;
    }
    public boolean removeAppointment(int id,int PTid){
        BookAppointmentService bookAppointmentService = (BookAppointmentService) sf.getService(sf.BOOKAPPOINTMENT_SERVICE);
        try{
            if(bookAppointmentService.cancelAppointment(id,PTid)){
                System.out.println("Appointment successfully canceled.");
                return true;
            }else{
                System.out.println("Appointment not canceled.");
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //CUSTOMER
    public boolean bookCourse(int courseId){
        BookCourseService bookCourseService = (BookCourseService) sf.getService(sf.BOOKCOURSE_SERVICE);
        try {
            if(bookCourseService.isSigned(courseId)){
                System.out.println("You are already signed up for this course.");
                return false;
            }else{
                if(bookCourseService.bookCourse(courseId)){
                    System.out.println("Course successfully booked.");
                    return true;
                }else{
                    System.out.println("Course is full");
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean cancelBooking(int courseId){
        BookCourseService bookCourseService = (BookCourseService) sf.getService(sf.BOOKCOURSE_SERVICE);
        try {
            if(bookCourseService.cancelBooking(courseId)){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer getCustomerByUsername(String username) throws SQLException {
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        return baseUserService.getCustomerByUsername(username);
    }

    public List<Customer> getAllCustomers(){
        BaseUserService baseUserService = (BaseUserService) sf.getService(sf.USER_SERVICE);
        List<Customer> customers = baseUserService.getAllCustomers();
        List<Customer> customerToRemove = new ArrayList<Customer>();
        for(Customer customer : customers){
            boolean pt = baseUserService.isPersonalTrainer(customer);
            if(pt){
                customerToRemove.add(customer);
            }
        }
        customers.removeAll(customerToRemove);
        return customers;
    }


    //SCHEDULE

    public void createSchedule(BaseUser baseUser, String newName) {
        if (baseUser.isValid()) {
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try {
                boolean done = scheduleService.createSchedule(baseUser, newName);
                if (!done) {
                    throw new CustomizedException("There has been an error in the creation of the schedule.");
                } else {
                    // Messaggio di successo
                    System.out.println("Schedule created successfully.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("User is not valid. Schedule creation aborted.");
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

        ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
        try{
                /*System.out.println("What do you want to change the name of the schedule to?");
                String newName = input.nextLine();
                schedule.setName(newName);
                 */
            boolean done = scheduleService.updateSchedule(schedule);
            if(!done)
                throw new CustomizedException("The update failed. You did not change the name of your schedule.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CustomizedException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Schedule> getAllSchedules() throws SQLException {
        ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
        return scheduleService.getAllSchedules();
    }
    public List<Schedule> getSchedulesByUsername(BaseUser baseUser){
        List <Schedule> schedules = new ArrayList<>();
        if(baseUser.isValid()){
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try{
                schedules = scheduleService.getSchedulesByUsername(baseUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return schedules;
    }
    //TRAININGS
    public void createTraining(Date date, Time startTime, Time endTime, String note, int scheduleID, String username) {
            TrainingService trainingService = (TrainingService) sf.getService(sf.TRAINING_SERVICE);

            try {
                boolean done = trainingService.createTraining((java.sql.Date) date, startTime, endTime, note, scheduleID, username);

                if (!done) {
                    throw new CustomizedException("There has been an error in the creation of the training.");
                }else System.out.println("The training has been created successfully.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                System.err.println(e.getMessage());
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date or time format. Please use the correct format: yyyy-MM-dd for date and HH:mm for time.");
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

    public void updateTraining(BaseUser baseUser, Training training){
        if (baseUser.isValid()) {
            TrainingService trainingService = (TrainingService) sf.getService(sf.TRAINING_SERVICE);
            try {
                boolean done = trainingService.updateTraining(training);
                if (!done) {
                    throw new CustomizedException("There has been an error in the update of the training.");
                } else {
                    System.out.println("Training successfully updated.");
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

    public void createExerciseDetail(int serie, int reps, int weight, int scheduleID, int exerciseID) {

        ExerciseDetailService exerciseDetailService = (ExerciseDetailService) sf.getService(sf.EXERCISEDETAIL_SERVICE   );
        try {
                /*// Richiesta dati per l'ExerciseDetail
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
                }*/
            boolean done = exerciseDetailService.createExerciseDetail(serie, reps, weight, scheduleID, exerciseID);

            if (!done) {
                throw new RuntimeException("There has been an error in the creation of the exercise detail.");
            } else {
                System.out.println("ExerciseDetail successfully created.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter valid numbers for serie, reps, weight, scheduleID, and exerciseID.");
        }

    }

    public void deleteExerciseDetail( ExerciseDetail exerciseDetail) {

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

    public void updateExerciseDetail(int id, int serie, int reps, int weight, int scheduleID, int exerciseID) {

        ExerciseDetailService exerciseDetailService = (ExerciseDetailService) sf.getService(sf.EXERCISEDETAIL_SERVICE   );
        try {
                /*System.out.println("Enter the new number of series:");
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
                }*/

            boolean done = exerciseDetailService.updateExerciseDetail(id, serie, reps, weight, exerciseID);
            if (!done) {
                throw new RuntimeException("There has been an error in the update of the exercise detail.");
            } else {
                System.out.println("ExerciseDetail successfully updated.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter valid numbers for serie, reps, weight, scheduleID, and exerciseID.");
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

    public List<ExerciseDetail> getExerciseDetailsBySchedule(int schedule_id) throws SQLException {
        ExerciseDetailService exerciseDetailService =  (ExerciseDetailService) sf.getService(sf.EXERCISEDETAIL_SERVICE);
        return exerciseDetailService.getExerciseDetailsBySchedule(schedule_id);

    }

    // EXERCISES

    // Solo PT (Personal Trainer)
    public void createExercise(String name, String category, String machine, String description) {

        ExerciseService exerciseService = (ExerciseService) sf.getService(sf.EXERCISE_SERVICE);
        try {
                /*// Inserimento dei dati per l'esercizio
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
*/
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
    }

    public String getExerciseNameById(int id) throws SQLException {
        ExerciseService exerciseService = (ExerciseService) sf.getService(sf.EXERCISE_SERVICE);
        return exerciseService.getExerciseNameById(id);
    }
    // Solo PT (Personal Trainer)
    public void deleteExercise(Exercise exercise) {

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

    public int getExerciseIdByName(String name) throws SQLException {
        ExerciseDetailService exerciseDetailService =  (ExerciseDetailService) sf.getService(sf.EXERCISEDETAIL_SERVICE);
        return exerciseDetailService.getExerciseIdByName(name);
    }

    //MACHINE

    public void createMachine(String name, String description, boolean state) {
        MachineService machineService = (MachineService) sf.getService(sf.MACHINE_SERVICE);
        try {
            /*System.out.println("Enter machine name:");
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
            }*/
            machineService.createMachine(name, description, state);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            /*System.out.println("Enter the new name:");
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
            }*/
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