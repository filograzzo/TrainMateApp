package DomainModel;

public class Training {
    private int id;
    private int customerId;//il piano allenamento è univocamente identificato da id del cliente e id della scheda
    private String plan;

    public Training(int id, int customerId, String plan) {
        this.id = id;
        this.customerId = customerId;
        this.plan = plan;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
