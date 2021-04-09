package pw.edu.wsg.employee;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {

    private static final Logger LOG = Logger.getLogger(EmployeeController.class.getName());

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    @PostMapping("/add")
    @ResponseStatus(CREATED)
    public int createEmployee( CreateEmployeeRequest request){
        LOG.info(format("Request createEmployee started: {request: %s}", request));

        Long employeeId = employeeService.createEmployee(request);
        LOG.info(format("Request createEmployee finished {employeeId: %s", employeeId));

        return 3121;
    }
}
