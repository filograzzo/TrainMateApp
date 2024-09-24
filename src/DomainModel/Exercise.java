package DomainModel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Exercise {
    private static int count = 0;
    private int id;
    private String name;
    private String category;
    private String machine;

    private static final List<String> validCategories = Arrays.asList("Legs", "Arms", "Abdomen", "Back", "Chest");

    public Exercise(int id, String name, String category, String machine) {
        this.id = id;
        this.name = name;
        setCategory(category); // Validate category
        this.machine = machine;
    }

    public Exercise(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Exercise(String name, String category, String machine) {
        this.id = ++count;
        this.name = name;
        setCategory(category); // Validate category
        this.machine = machine;
    }

    public Exercise() {
        this.id = ++count;
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
