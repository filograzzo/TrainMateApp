package DomainModel;

import java.util.ArrayList;

public class PersonalTrainer extends BaseUser {

    //Questa classe è una classe di oggetti temporanei: i dati restano sempre salvati nel database e solo al momento del login
    //voglio compiere un'azione allora viene creato un oggetto Personal trainer che, essendo il costruttore package private,
    //può essere chiamato solo all'interno dello stesso package da altri metodi (nel nostro caso solo dal metodo per il login).

    //fixme: trovare un metodo per rendere questo costruttore non pubblico ma allo stesso tempo accedibile solo da service e dao



    //TODO:aggiungere una classe agenda e avere un oggetto agenda come attributo del PT
    public PersonalTrainer(int id, String username, String password, String email) {
        super(id, username, password, email);
    }

    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Appointment> appointments= new ArrayList<>();
    public boolean addAppointmentToList(Appointment appointment) {
        appointments.add(appointment);
        return true;
    }
    public boolean addCourseToList(Course course) {
        courses.add(course);
        return true;
    }
    public Course getCoursebyId(int id){
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }
    public ArrayList<Appointment> getAppointmentList(){
        return appointments;
    }


}