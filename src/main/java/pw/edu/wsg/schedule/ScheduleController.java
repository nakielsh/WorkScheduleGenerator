package pw.edu.wsg.schedule;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.employee.EmployeeService;
import pw.edu.wsg.registration.RegistrationService;

import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

@Controller
@RequestMapping(path = "/home")
public class ScheduleController {

    private final EmployeeService employeeService;
    private static final Logger LOG = Logger.getLogger(RegistrationService.class.getName());
    private Schedule schedule1;

    public ScheduleController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public String showHomepage(Model model){
        model.addAttribute("schedule", new Schedule());
        return "home-page";
    }

    @PostMapping("/add-employee")
    public String addEmployee(Schedule schedule, Model model){
        schedule.setDaysInMonth();
        LOG.info(format("Schedule : %s", schedule));
        int month = schedule.getMonth();
        int year = schedule.getYear();
        int days = schedule.getDaysInMonth();
        model.addAttribute("pickedMonth", month);
        model.addAttribute("pickedYear", year);
        model.addAttribute("days", days);
        model.addAttribute("addedEmployee", new Employee());
        this.schedule1 = schedule;
        return "add-employee";
    }

    @PostMapping("/add-employees")
    public String addEmployees(Employee addedEmployee, Model model ){
        LOG.info(format("Schedule : %s", schedule1));
        LOG.info(format("Employee : %s", addedEmployee));
        this.schedule1.addToEmployeeList(addedEmployee);
        LOG.info(format("Added Schedule : %s", schedule1));

        List<Employee> employeeList = schedule1.getEmployeeList();

        model.addAttribute("employeeList", employeeList);

        model.addAttribute("pickedMonth", schedule1.getMonth());
        model.addAttribute("pickedYear", schedule1.getYear());
        model.addAttribute("days", schedule1.getDaysInMonth());
        model.addAttribute("addedEmployee", new Employee());
        return "add-employees";


    }

}
