package pw.edu.wsg.schedule;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.employee.EmployeeRepository;
import pw.edu.wsg.employee.EmployeeService;
import pw.edu.wsg.registration.RegistrationService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.String.format;

@Controller
@RequestMapping(path = "/home")
public class ScheduleController {

    private final EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    private final ScheduleService scheduleService;
    private static final Logger LOG = Logger.getLogger(RegistrationService.class.getName());
    private Schedule schedule1;

    public ScheduleController(EmployeeService employeeService, ScheduleService scheduleService) {
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
    }


    @GetMapping("")
    public String showHomepage(Model model) {
        model.addAttribute("schedule", new Schedule());
        return "home-page";
    }

    @PostMapping("/add-employee")
    public String addEmployee(Schedule schedule, Model model) {
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
        schedule1.setMonthName();
        model.addAttribute("schedule",schedule1);
        return "add-employee";
    }

    @PostMapping("/add-employees")
    public String addEmployees(Employee addedEmployee, Model model) {
        LOG.info(format("Schedule : %s", schedule1));
        List<Employee> employeeList = schedule1.getEmployeeList();
        Employee employeeToRemove = null;

        if (addedEmployee != null) {
            for (Employee employee : employeeList) {
                if (employee.getName().equals(addedEmployee.getName())) {
                    employeeToRemove = employee;
                }
            }
            if (employeeToRemove != null) {
                employeeList.remove(employeeToRemove);
                model.addAttribute("isEdited", true);
            }

            employeeList.add(addedEmployee);
            schedule1.addToEmployeeList(addedEmployee);
            LOG.info(format("Employee : %s", addedEmployee));
            scheduleService.addEmployee(addedEmployee);
            model.addAttribute("notification",
                    String.format("Contractor \"%s\" successfully saved", addedEmployee.getName()));
            model.addAttribute("action", "save");

            if (model.getAttribute("isEdited") == null) {
                model.addAttribute("isAdded", true);
            }
            model.addAttribute("name", addedEmployee.getName());
        }

        LOG.info(format("Added Schedule : %s", schedule1));

        model.addAttribute("employeeList", employeeList);

        model.addAttribute("pickedMonth", schedule1.getMonth());
        model.addAttribute("schedule", schedule1);
        model.addAttribute("pickedYear", schedule1.getYear());
        model.addAttribute("days", schedule1.getDaysInMonth());
        model.addAttribute("addedEmployee", new Employee());

        return "add-employees";
    }

    @GetMapping("/post-delete-add")
    public String addEmployeesAfterDelete(Model model) {
        LOG.info(format("Schedule : %s", schedule1));
        List<Employee> employeeList = schedule1.getEmployeeList();


        LOG.info(format("Added Schedule : %s", schedule1));

        model.addAttribute("employeeList", employeeList);

        model.addAttribute("schedule", schedule1);
        model.addAttribute("pickedMonth", schedule1.getMonth());
        model.addAttribute("pickedYear", schedule1.getYear());
        model.addAttribute("days", schedule1.getDaysInMonth());
        model.addAttribute("addedEmployee", new Employee());

        return "add-employees";
    }


    @GetMapping("/delete-employee/{name}")
    public String deleteEmployee(@PathVariable(name = "name") String deletedEmployeeName, RedirectAttributes redirectAttributes) {

        if (!schedule1.getEmployeeList().isEmpty()) {
            schedule1.getEmployeeList().removeIf(emp -> emp.getName().equals(deletedEmployeeName));
        }

        List<Employee> employeeList = schedule1.getEmployeeList();
        redirectAttributes.addAttribute("isDeleted", true);
        redirectAttributes.addAttribute("name", deletedEmployeeName);


        return "redirect:/home/post-delete-add";
    }

    @GetMapping("/generated/based-on-equality")
    public String showBOESchedule(Model model){
        Schedule generatedSchedule = scheduleService.generateScheduleBOE(schedule1);
        Map<Integer, Employee> map = generatedSchedule.getDictionary();
        List<Employee> employeeList = generatedSchedule.getEmployeeList();
        Map<Integer, List<String>> emptyDays = generatedSchedule.findEmptyDays();

        model.addAttribute("map", map);
        model.addAttribute("schedule", schedule1);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("emptymap", emptyDays);

        return "schedule-view";
    }

    @GetMapping("/generated/based-on-equality2")
    public String showBOE2Schedule(Model model){
        Schedule generatedSchedule = scheduleService.generateScheduleBOE2(schedule1);
        Map<Integer, Employee> map = generatedSchedule.getDictionary();
        List<Employee> employeeList = generatedSchedule.getEmployeeList();
        Map<Integer, List<String>> emptyDays = generatedSchedule.findEmptyDays();

        model.addAttribute("map", map);
        model.addAttribute("schedule", schedule1);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("emptymap", emptyDays);

        return "schedule-view";
    }


}
