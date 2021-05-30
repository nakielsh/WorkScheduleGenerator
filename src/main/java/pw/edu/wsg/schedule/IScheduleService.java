package pw.edu.wsg.schedule;

import pw.edu.wsg.employee.Employee;

public interface IScheduleService {

    void addEmployee(Employee employee);

    Schedule generateScheduleBOE(Schedule schedule);

    Schedule generateScheduleBOEMul(Schedule schedule, int maxPeopleForDay);

    String[] getEmployeeNameList();
}
