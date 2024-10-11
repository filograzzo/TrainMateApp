package BusinessLogic.Service.PersonalTrainer;

import DAO.ExerciseDAO;
import DAO.MachineDAO;
import DomainModel.BaseUser;
import DomainModel.Exercise;
import DomainModel.Machine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MachineService {
    MachineDAO machineDAO;
    ExerciseDAO exerciseDAO;
    BaseUser baseUser;

    public MachineService(MachineDAO machineDAO, ExerciseDAO exerciseDAO) {
        this.machineDAO = machineDAO;
        this.exerciseDAO = exerciseDAO;
    }

    public void setCurrentUser(BaseUser baseUser) {
        this.baseUser = baseUser;
    }

    public boolean createMachine(String name, String description, Boolean state) throws SQLException {
        return machineDAO.addMachine(name, description, state);
    }

    public boolean deleteMachine(Machine machine) throws SQLException {
        return machineDAO.removeMachineBy(machine);
    }

    public boolean updateMachine(Machine machine) throws SQLException {
        return machineDAO.updateMachine(machine);
    }

    public List<Machine> getAllMachines() throws SQLException {
        return machineDAO.getAllMachines();
    }

    public List<Exercise> getExercisesByMachine(Machine machine) throws SQLException {
        return exerciseDAO.getExercisesByMachine(machine);
    }

    public Machine getMachineByName(String machineName) throws SQLException {
        return machineDAO.getMachineByName(machineName);
    }

    public Machine getMachineById(int machineId) throws SQLException {
        return machineDAO.getMachineById(machineId);
    }

    public boolean getMachineState(Machine machine) throws SQLException {
        Machine machine1 = machineDAO.getMachineById(machine.getId());
        return machine1 != null && machine.getState();
    }

    public List<Machine> getActiveMachines() throws SQLException {
        List<Machine> allMachines = machineDAO.getAllMachines();
        List<Machine> activeMachines = new ArrayList<>();

        for (Machine machine : allMachines) {
            if (machine.getState()) {
                activeMachines.add(machine);
            }
        }
        return activeMachines;
    }

    public List<Machine> getInactiveMachines() throws SQLException {
        List<Machine> allMachines = machineDAO.getAllMachines();
        List<Machine> inactiveMachines = new ArrayList<>();

        for (Machine machine : allMachines) {
            if (!machine.getState()) {
                inactiveMachines.add(machine);
            }
        }
        return inactiveMachines;
    }

    public String getMachineNameById(int machine_id) throws SQLException {
        return machineDAO.getMachineNameById(machine_id);
    }
}
