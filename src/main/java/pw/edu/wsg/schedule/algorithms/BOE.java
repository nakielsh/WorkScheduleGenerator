package pw.edu.wsg.schedule.algorithms;

import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.schedule.Schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOE {

    private Schedule schedule;
    private int leastWantedDay;
    private Employee leastAvailableEmployee;
    private Map<Integer, Integer> peopleForDay = new HashMap<>();
    private Map<Integer, Employee> realSchedule = new HashMap<>();
    private int maxWorkingDays;

    public BOE(Schedule schedule) {
        this.schedule = schedule;
    }


    public Schedule generateSchedule() {

        createMap();
        countMaxWorkingDays();
        setAllDaysLeft();

        for (int i = 1; i <= schedule.getDaysInMonth(); i++) {
            findLeastWantedDay();
            leastAvailableEmployee = findLeastAvailableEmployeeThisDay(leastWantedDay);
            setEmployeeToDay(leastWantedDay, leastAvailableEmployee);
            peopleForDay.replace(leastWantedDay, 0);
            decrementDaysLeft(leastAvailableEmployee);
        }

        schedule.setDictionary(realSchedule);

        return schedule;
    }

    public void createMap() {
        for (Integer i = 1; i <= schedule.getDaysInMonth(); i++) {
            peopleForDay.put(i, 0);
            realSchedule.put(i, new Employee());
        }
    }

    public void findLeastWantedDay() {
        for (Employee employee : schedule.getEmployeeList()) {
            assert employee.getAvailability() != null;
            for (Integer day : employee.getAvailability()) {
                if (peopleForDay.containsKey(day)) {
                    Integer tmp = peopleForDay.get(day);
                    tmp += 1;
                    peopleForDay.replace(day, tmp);
                }
            }
        }

        leastWantedDay = 1;

        for (Integer key : peopleForDay.keySet()) {
            if (peopleForDay.get(key) > peopleForDay.get(leastWantedDay)) {
                if (peopleForDay.get(key) != 0){
                    leastWantedDay = key;
                }
            }
        }

    }

    public Employee findLeastAvailableEmployeeThisDay(int day) {
        int min = 100;
        Employee leastEmployee = new Employee();
        for (Employee employee : schedule.getEmployeeList()) {
            assert employee.getAvailability() != null;
            if (employee.getAvailability().size() < min) {
                if (employee.getDaysLeft() != 0) {
                    leastEmployee = employee;
                    min = leastEmployee.getAvailability().size();
                }
            }
        }
        return leastEmployee;
    }

    public void countMaxWorkingDays() {
        maxWorkingDays = (schedule.getDaysInMonth() / schedule.getEmployeeList().size()) + 1;
    }

    public void setEmployeeToDay(int day, Employee employee) {
        realSchedule.replace(day, employee);
    }

    public void setAllDaysLeft() {
        for (Employee employee : schedule.getEmployeeList()) {
            employee.setDaysLeft(maxWorkingDays);
        }
    }

    public void decrementDaysLeft(Employee employee) {
        for (Employee employee1 : schedule.getEmployeeList()) {
            if (employee1.getName().equals(employee.getName())) {
                employee1.decrementDaysLeft();
            }
        }
    }

}
