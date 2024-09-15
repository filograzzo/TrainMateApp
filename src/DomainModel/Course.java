package DomainModel;

public class Course {
    private String name;
    private String description;
    private int maxParticipants;
    private int participants;
    private String trainer;


    public Course(String name, String description, int maxParticipants, int participants, String trainer, String schedule, String location, String type, String level, String duration, String price, String image) {
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.participants = participants;
        this.trainer = trainer;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public int getParticipants() {
        return participants;
    }

    public String getTrainer() {
        return trainer;
    }


}
