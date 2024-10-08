package DomainModel;

import java.util.List;

public class Machine {
    private int id;
    private String name;
    private String description;
    private boolean state;
    private List<Exercise> exercises;

    public Machine(int id, String name, String description, boolean state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getState() { return state; }
    public void setState(boolean state) { this.state = state; }
}