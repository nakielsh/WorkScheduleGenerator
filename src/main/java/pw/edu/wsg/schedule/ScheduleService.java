package pw.edu.wsg.schedule;

import org.springframework.stereotype.Service;
import pw.edu.wsg.employee.CreateEmployeeRequest;
import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.employee.EmployeeRepository;
import pw.edu.wsg.employee.EmployeeService;
import pw.edu.wsg.schedule.algorithms.BOE;

@Service
public class ScheduleService {

    private EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;


    public ScheduleService(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    public Object addEmployee(Employee employee) {
        boolean employeeExists = !employeeRepository
                .findByName(employee.getName()).isEmpty();
        Long id;

        if (!employeeExists) {
            id = employeeService.createEmployee(new CreateEmployeeRequest(employee.getName(), employee.getAvailability()));
            return employeeService.getEmployee(id);
        } else {
            Employee previousEmployee = employeeRepository.findByName(employee.getName()).get(0);
            previousEmployee.setAvailability(employee.getAvailability());

            return previousEmployee;

        }

    }

    public Schedule generateSchedule(Schedule schedule){
        BOE boe = new BOE(schedule);
        Schedule generatedSchedule = boe.generateSchedule();

        return generatedSchedule;
    }
}
