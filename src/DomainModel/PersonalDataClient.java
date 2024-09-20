package DomainModel;

public class PersonalDataClient {
    private int id;
    private float height;
    private float weight;
    private int age;
    private String gender;
    private String activity;  //vedere la propria scheda
    private String goal;  //cosa vuoi ottenere

    public PersonalDataClient(int id, float height, float weight, int age,String gender, String activity, String goal) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender=gender;
        this.activity = activity;
        this.goal = goal;
    }
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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

    public String getActivity(){
        return activity;
    }

    public void setActivity(String activity){
        this.activity = activity;
    }

    public String getGoal(){
        return goal;
    }

    public void setGoal(String goal){
        this.goal = goal;
    }
    // Calcolo del BMI
    private double calculateBMI() {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }


}
