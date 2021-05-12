package pw.edu.wsg.employee;

import org.springframework.stereotype.Service;
import pw.edu.wsg.appuser.AppUserRepositoryService;


import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

@Service
public class EmployeeRepositoryService implements EmployeeService {

    private static final Logger LOG = Logger.getLogger(EmployeeRepositoryService.class.getName());

    private final EmployeeRepository employeeRepository;
    private AppUserRepositoryService appUserRepositoryService;

    public EmployeeRepositoryService(EmployeeRepository employeeRepository, AppUserRepositoryService appUserRepositoryService) {
        this.employeeRepository = employeeRepository;
        this.appUserRepositoryService = appUserRepositoryService;
    }


    @Override
    public Long createEmployee(CreateEmployeeRequest request) {
        LOG.info(format("Saving employee: %s", request));

        Employee newEmployee = new Employee(request.getName(), request.getAvailability(), appUserRepositoryService.getId());
        Employee savedEmployee = employeeRepository.save(newEmployee);
        Long newEmployeeId = savedEmployee.getId();

        LOG.info(format("Saved employee: {id: %d}", newEmployeeId));

        return newEmployeeId;
    }

    @Override
    public List<Employee> getEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(Long id) {
        return employeeRepository.getOne(id);
    }

}
