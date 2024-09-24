package DomainModel;

public class Exercise {
    private static int count = 0;
    private int id;
    private String name;
    private Category category;
    private Machine machine;

    public Exercise(int id, String name, Category category, Machine machine) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.machine = machine;
    }

    public Exercise(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Exercise(String name, Category category, Machine machine) {
        this.id = ++count;
        this.name = name;
        this.category = category;
        this.machine = machine;
    }

    public Exercise() {
        this.id = ++count;
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

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        if (Category.getCategories().contains(category)) {
            this.category = category;
        } else {
            throw new IllegalArgumentException("Invalid category");
        }
    }


    public Machine getMachine() {
        return machine;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
