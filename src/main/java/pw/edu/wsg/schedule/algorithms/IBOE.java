package pw.edu.wsg.schedule.algorithms;

import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.schedule.Schedule;

public interface IBOE {

    Schedule generateSchedule();

    void createMap();

    void countDaysWithoutPeople();

    int findLeastWantedDay();

    void fillEmptyDays();

    Employee findLeastAvailableEmployeeThisDay(int day);

    void countMaxWorkingDays();

    void setEmployeeToDay(int day, Employee employee);

    void decrementDaysLeft(Employee employee);

    void incrementDaysLeft(Employee employee);

    void setNumberOfDays();

    void setAllDaysLeft();
}
