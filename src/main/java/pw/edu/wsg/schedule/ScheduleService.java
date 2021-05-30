package pw.edu.wsg.schedule;

import org.springframework.stereotype.Service;
import pw.edu.wsg.appuser.AppUserRepositoryService;
import pw.edu.wsg.employee.CreateEmployeeRequest;
import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.employee.EmployeeRepository;
import pw.edu.wsg.employee.EmployeeService;
import pw.edu.wsg.schedule.algorithms.BOE;
import pw.edu.wsg.schedule.algorithms.BOEMul;

import java.util.Arrays;
import java.util.List;

@Service
public class ScheduleService implements IScheduleService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final AppUserRepositoryService appUserRepositoryService;


    public ScheduleService(EmployeeRepository employeeRepository, EmployeeService employeeService, AppUserRepositoryService appUserRepositoryService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.appUserRepositoryService = appUserRepositoryService;
    }

    public void addEmployee(Employee employee) {
        boolean employeeExists = !employeeRepository
                .findByName(employee.getName()).isEmpty();
        Long id;

        if (!employeeExists) {
            id = employeeService.createEmployee(new CreateEmployeeRequest(employee.getName(), employee.getAvailability()));
            employeeService.getEmployee(id);
        } else {
            Employee previousEmployee = employeeRepository.findByName(employee.getName()).get(0);
            previousEmployee.setAvailability(employee.getAvailability());

        }

    }

    public Schedule generateScheduleBOE(Schedule schedule) {
        BOE boe = new BOE(schedule);

        return boe.generateSchedule();
    }


    public Schedule generateScheduleBOEMul(Schedule schedule, int maxPeopleForDay) {
        BOEMul boe = new BOEMul(schedule, maxPeopleForDay);

        return boe.generateSchedule();
    }

    public String[] getEmployeeNameList() {
        List<Employee> everyEmployee = employeeRepository.findByAppUserId(appUserRepositoryService.getId());
        String[] everyEmployeeName = new String[everyEmployee.size()];
        for (int i = 0; i < everyEmployee.size(); i++) {
            everyEmployeeName[i] = everyEmployee.get(i).getName();
        }

        System.out.println(Arrays.toString(everyEmployeeName));
        System.out.println(everyEmployee);
        return everyEmployeeName;
    }
}
