package pw.edu.wsg.schedule;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pw.edu.wsg.appuser.AppUserRepositoryService;
import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.employee.EmployeeRepository;
import pw.edu.wsg.employee.EmployeeService;
import pw.edu.wsg.registration.RegistrationService;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.String.format;

@Controller
@RequestMapping(path = "/home")
public class ScheduleController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final ScheduleService scheduleService;
    private final AppUserRepositoryService appUserRepositoryService;
    private static final Logger LOG = Logger.getLogger(RegistrationService.class.getName());
    private Schedule schedule1;

    public ScheduleController(EmployeeService employeeService, EmployeeRepository employeeRepository, ScheduleService scheduleService, AppUserRepositoryService appUserRepositoryService) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.scheduleService = scheduleService;
        this.appUserRepositoryService = appUserRepositoryService;
    }


    @GetMapping("")
    public String showHomepage(Model model) {
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("appuser", appUserRepositoryService.getUsername());
        return "home-page";
    }

    @PostMapping("/add-employee")
    public String addEmployee(Schedule schedule, Model model) {
        schedule.setDaysInMonth();
        LOG.info(format("Schedule : %s", schedule));
        int month = schedule.getMonth();
        int year = schedule.getYear();
        int days = schedule.getDaysInMonth();
        model.addAttribute("appuser", appUserRepositoryService.getUsername());
        model.addAttribute("pickedMonth", month);
        model.addAttribute("pickedYear", year);
        model.addAttribute("days", days);
        model.addAttribute("addedEmployee", new Employee());
        this.schedule1 = schedule;
        schedule1.setMonthName();
        model.addAttribute("schedule",schedule1);
        model.addAttribute("employeeNameList", scheduleService.getEmployeeNameList());
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

        if(schedule1.getEmployeeList().isEmpty()){
            model.addAttribute("isNotEmpty", false);
        }else{
            model.addAttribute("isNotEmpty", true);
        }

        LOG.info(format("Added Schedule : %s", schedule1));

        model.addAttribute("employeeList", employeeList);

        model.addAttribute("appuser", appUserRepositoryService.getUsername());
        model.addAttribute("pickedMonth", schedule1.getMonth());
        model.addAttribute("schedule", schedule1);
        model.addAttribute("pickedYear", schedule1.getYear());
        model.addAttribute("days", schedule1.getDaysInMonth());
        model.addAttribute("addedEmployee", new Employee());
        model.addAttribute("employeeNameList", scheduleService.getEmployeeNameList());

        return "add-employees";
    }

    @GetMapping("/post-delete-add")
    public String addEmployeesAfterDelete(Model model) {

        if(schedule1 == null){
            return "redirect:/home";
        }

        if(schedule1.getEmployeeList().isEmpty()){
            model.addAttribute("isNotEmpty", false);
        }else{
            model.addAttribute("isNotEmpty", true);
        }

        LOG.info(format("Schedule : %s", schedule1));
        List<Employee> employeeList = schedule1.getEmployeeList();


        LOG.info(format("Added Schedule : %s", schedule1));

        model.addAttribute("employeeList", employeeList);
        model.addAttribute("appuser", appUserRepositoryService.getUsername());

        model.addAttribute("schedule", schedule1);
        model.addAttribute("pickedMonth", schedule1.getMonth());
        model.addAttribute("pickedYear", schedule1.getYear());
        model.addAttribute("days", schedule1.getDaysInMonth());
        model.addAttribute("addedEmployee", new Employee());
        model.addAttribute("employeeNameList", scheduleService.getEmployeeNameList());

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
        Map<Integer, Employee> map = generatedSchedule.getRealSchedule1();
        List<Employee> employeeList = generatedSchedule.getEmployeeList();
        Map<Integer, List<String>> emptyDays = generatedSchedule.findEmptyDaysInSchedule1();

        model.addAttribute("appuser", appUserRepositoryService.getUsername());
        model.addAttribute("map", map);
        model.addAttribute("schedule", schedule1);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("emptymap", emptyDays);
        model.addAttribute("BOE1", true);

        return "schedule-view";
    }

    @GetMapping("/generated/based-on-equality2")
    public String showBOE2Schedule(Model model){
//        Schedule generatedSchedule = scheduleService.generateScheduleBOE2(schedule1);
//        Map<Integer, List<Employee>> map = generatedSchedule.getRealScheduleMulti();
//        List<Employee> employeeList = generatedSchedule.getEmployeeList();
//        Map<Integer, List<String>> emptyDays = generatedSchedule.findEmptyDaysInScheduleMulti();

        Schedule generatedSchedule = scheduleService.generateScheduleBOEMul(schedule1);
        Map<Integer, List<Employee>> map = generatedSchedule.getRealScheduleMulti();
        List<Employee> employeeList = generatedSchedule.getEmployeeList();
        Map<Integer, List<String>> emptyDays = generatedSchedule.findEmptyDaysInScheduleMulti();

        model.addAttribute("appuser", appUserRepositoryService.getUsername());
        model.addAttribute("map", map);
        model.addAttribute("schedule", schedule1);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("emptymap", emptyDays);
        model.addAttribute("BOEMore", true);

        return "schedule-view";
    }

    @GetMapping("/list-of-employees")
    public String showAllEmployees(Model model){
        System.out.println(appUserRepositoryService.getId());
        model.addAttribute("appuser", appUserRepositoryService.getUsername());
        List<Employee> everyEmployee = employeeRepository.findByAppUserId(appUserRepositoryService.getId());
        System.out.println(everyEmployee);
        model.addAttribute("employeeList", everyEmployee);
        return "employee-list";
    }

    @Transactional
    @GetMapping("/delete-employee-from-db/{name}")
    public String deleteEmployeeFromDB(@PathVariable(name = "name") String deletedEmployeeName, RedirectAttributes redirectAttributes) {

        employeeRepository.deleteEmployeeByName(deletedEmployeeName);
        redirectAttributes.addAttribute("isDeleted", true);
        redirectAttributes.addAttribute("name", deletedEmployeeName);

        return "redirect:/home/list-of-employees";
    }


}
