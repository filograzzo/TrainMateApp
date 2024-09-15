package DomainModel;

public class Signed {
    private int courseId;
    private int customerId;

    public Signed(int courseId, int customerId) {
        this.courseId = courseId;
        this.customerId = customerId;
    }

    // Getter e Setter
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

}
