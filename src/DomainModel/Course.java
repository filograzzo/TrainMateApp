package DomainModel;

public class Course {
    int id;
    private String name;
    private String bodyPartsTrained;
    private int maxParticipants;


    private int participants;
    private int trainerID;


    public Course(int id,String name, int maxParticipants, int participants, int trainerID, String bodyPartsTrained) {
        this.id = id;
        this.name = name;
        this.maxParticipants = maxParticipants;
        this.participants = participants;
        this.trainerID = trainerID;
        this.bodyPartsTrained = bodyPartsTrained;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getMaxParticipants() {
        return maxParticipants;
    }
    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getParticipants() {
        return participants;
    }
    public int getIDTrainer() {
        return trainerID;
    }
    public String getBodyPartsTrained() {
        return bodyPartsTrained;
    }

}
