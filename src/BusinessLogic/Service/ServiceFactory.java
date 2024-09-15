package BusinessLogic.Service;

public class ServiceFactory {
    private static ServiceFactory instance;
    public final int USER_SERVICE = 1;
    public final int PROFILEPT_SERVICE = 2;
    public final int PERSONALTRAINERHOME_SERVICE = 3;
    public final int AGENDA_SERVICE = 4;
    public final int WORKOUT_SERVICE = 5;
    public final int PROFILE_SERVICE = 6;
    public final int CUSTOMERHOME_SERVICE = 7;
    public final int BOOKCOURSE_SERVICE = 8;
    public final int BOOKAPPOINTMENT_SERVICE = 9;
    public static ServiceFactory getInstance() {
        if (instance == null)
            instance = new ServiceFactory();
        return instance;
    }
    public Object getService(int serviceType) {
        switch (serviceType) {
            case USER_SERVICE:
                return ServiceManager.getInstance().getUserService();
            case PROFILEPT_SERVICE:
                return ServiceManager.getInstance().getProfilePTService();
            case PERSONALTRAINERHOME_SERVICE:
                return ServiceManager.getInstance().getPersonalTrainerHomeService();
            case AGENDA_SERVICE:
                return ServiceManager.getInstance().getAgendaService();
            case WORKOUT_SERVICE:
                return ServiceManager.getInstance().getWorkoutService();
            case PROFILE_SERVICE:
                return ServiceManager.getInstance().getProfileService();
            case CUSTOMERHOME_SERVICE:
                return ServiceManager.getInstance().getCustomerHomeService();
            case BOOKCOURSE_SERVICE:
                return ServiceManager.getInstance().getBookCourseService();
            case BOOKAPPOINTMENT_SERVICE:
                return ServiceManager.getInstance().getBookAppointmentService();
            default:
                return null;
        }
    }

}
