package DomainModel;

import java.util.Arrays;
import java.util.List;

public class Exercise {
    private int id;
    private String name;
    private String category;
    private String machine;
    private String description;

    private static final List<String> validCategories = Arrays.asList("Legs", "Arms", "Abdomen", "Back", "Chest", "Shoulders");

    public Exercise(int id, String name, String category, String machine, String description) {
        this.id = id;
        this.name = name;
        setCategory(category); // Validate category
        this.machine = machine;
        this.description = description;
    }

    public Exercise(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for category with validation
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (validCategories.contains(category)) {
            this.category = category;
        } else {
            throw new IllegalArgumentException("Invalid category name: " + category);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for machine
    public String getMachine() {
        return machine;
    }
    public void setMachine(String machine) {
        this.machine = machine;
    }

    // Static method to get all valid categories
    public static List<String> getValidCategories() {
        return validCategories;
    }
}