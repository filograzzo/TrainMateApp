package BusinessLogic.Service;

public class ServiceFactory {
    private static ServiceFactory instance;
    public final int USER_SERVICE = 1;
    public final int PROFILEPT_SERVICE = 2;
    public final int AGENDA_SERVICE = 3;
    public final int PROFILE_SERVICE = 4;
    public final int BOOKCOURSE_SERVICE = 5;
    public final int BOOKAPPOINTMENT_SERVICE = 6;
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
            case AGENDA_SERVICE:
                return ServiceManager.getInstance().getAgendaService();
            case PROFILE_SERVICE:
                return ServiceManager.getInstance().getProfileService();
            case BOOKCOURSE_SERVICE:
                return ServiceManager.getInstance().getBookCourseService();
            case BOOKAPPOINTMENT_SERVICE:
                return ServiceManager.getInstance().getBookAppointmentService();
            default:
                return null;
        }
    }

}
