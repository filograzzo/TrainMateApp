package DomainModel;

import java.util.ArrayList;

public class AppointmentList {
    private int id;
    private ArrayList<Appointment> appointments;
    public AppointmentList(int id) {
        this.id = id;
        this.appointments = new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
    public void removeAppointment(int idAppointment) {
        //cerco l'aapuntamento da rimuovere
        boolean found = false;
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId() == idAppointment) {
                appointments.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Appointment not found");
        }

    }




}
