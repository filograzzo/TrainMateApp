package DomainModel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Category {
    private static final List<String> categories = Arrays.asList("Legs", "Arms", "Abdomen", "Back", "Chest");
    private String name;

    public Category(String name) {
        boolean found = false;
        int i = 0;
        while (!found && i < categories.size()) {
            if (Objects.equals(name, categories.get(i))) {
                found = true;
                this.name = categories.get(i);
            }
            i++;
        }
        if (!found) {
            throw new IllegalArgumentException("Invalid category name: " + name);
        }
    }


    public static List<String> getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        boolean found = false;
        int i = 0;
        while (!found && i < categories.size()) {
            if (Objects.equals(name, categories.get(i))) {
                found = true;
                this.name = categories.get(i);
                return true;
            }
            i++;
        }
        if (!found) {
            throw new IllegalArgumentException("Invalid category name: " + name);
        }
        return false;
    }
}

