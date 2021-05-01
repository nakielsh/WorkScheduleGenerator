package pw.edu.wsg.employee;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    Long createEmployee(CreateEmployeeRequest request);

    public List<Employee> getEmployees();

    public Employee getEmployee(Long id);
}
