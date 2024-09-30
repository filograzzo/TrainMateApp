package BusinessLogic.Service;

import DAO.ExerciseDetailDAO;
import DAO.ScheduleDAO;
import DomainModel.BaseUser;
import DomainModel.ExerciseDetail;
import DomainModel.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScheduleService {
    private ScheduleDAO scheduleDAO;
    private ExerciseDetailDAO exerciseDetailDAO;
    private BaseUser currentUser;

    public ScheduleService(ScheduleDAO scheduleDAO, ExerciseDetailDAO exerciseDetailDAO){
        this.scheduleDAO = scheduleDAO;
        this.exerciseDetailDAO = exerciseDetailDAO;
    }
    public void setCurrentUser(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public boolean createSchedule(BaseUser baseUser, String scheduleName) throws SQLException {
        boolean done = scheduleDAO.addSchedule(scheduleName, baseUser.getUsername());
        return done;
    }

    public boolean removeSchedule(Schedule schedule) throws SQLException {
        boolean done = scheduleDAO.removeScheduleById(schedule.getId());
        return done;
    }

    public List<Schedule> getSchedules(BaseUser baseUser) throws SQLException {
        List<Schedule> schedules = scheduleDAO.getSchedulesByUsername(baseUser.getUsername());

        for (Schedule schedule : schedules){
            schedule.setExercises(exerciseDetailDAO.getExerciseDetailsByScheduleId(schedule.getId()));
        }

        return schedules;
    }

    //update of a schedule
    public boolean updateSchedule(Schedule schedule) throws SQLException {
        if(schedule != null){
            scheduleDAO.updateSchedule(schedule);
            return true;
        }else return false;
    }

}