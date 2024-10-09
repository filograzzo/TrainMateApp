package DomainModel;

import java.util.ArrayList;

public class Customer extends BaseUser {

    //Questa classe è una classe di oggetti temporanei: i dati restano sempre salvati nel database e solo al momento del login
    //voglio compiere un'azione allora viene creato un oggetto User che, essendo il costruttore package private,
    //può essere chiamato solo all'interno dello stesso package da altri metodi (nel nostro caso solo dal metodo per il login).
    private ArrayList<Course> courses;
    private PersonalTrainer personalTrainer;
    private float height;
    private float weight;
    private int age;

    private String gender;
    // creare oggetto scheda
    private String goal;  //cosa vuoi ottenere
    //todo aggiungere lista di corsi prenotati

    public Customer(int id, String username, String password, String email) {
      super(id,username, password, email);

    }
    private AppointmentList appointmentList=new AppointmentList(this.getId());
    public AppointmentList getAppointmentList() {
        return appointmentList;
    }
    public float getHeight(){
        return height;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public float getWeight(){
        return weight;
    }

    public void setWeight(float weight){
        this.weight = weight;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }
    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getGoal(){
        return goal;
    }

    public void setGoal(String goal){
        this.goal = goal;
    }



}