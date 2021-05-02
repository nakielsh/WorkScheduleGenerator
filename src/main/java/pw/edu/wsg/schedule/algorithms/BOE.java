package pw.edu.wsg.schedule.algorithms;

import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.schedule.Schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOE {

    private final Schedule schedule;
    private int leastWantedDay;
    private Employee leastAvailableEmployee;
    private final Map<Integer, Integer> peopleForDay = new HashMap<>();
    private final Map<Integer, Employee> realSchedule = new HashMap<>();
    private int maxWorkingDays;
    private int daysWithoutPeople = 0;

    public BOE(Schedule schedule) {
        this.schedule = schedule;
    }


    public Schedule generateSchedule() {

        createMap();
        countMaxWorkingDays();
        setAllDaysLeft();
        findLeastWantedDay();
        countDaysWithoutPeople();

        for (int i = 1; i <= schedule.getDaysInMonth() - daysWithoutPeople; i++) {
            findLeastWantedDay();
//            System.out.println(peopleForDay);
            leastAvailableEmployee = findLeastAvailableEmployeeThisDay(leastWantedDay);
            setEmployeeToDay(leastWantedDay, leastAvailableEmployee);
            peopleForDay.replace(leastWantedDay, 0);
            decrementDaysLeft(leastAvailableEmployee);
        }
        System.out.println("\tBefore optimalization:");
        for (Employee employee : schedule.getEmployeeList()) {
            System.out.println(employee.getName() + " days left: " + employee.getDaysLeft());
        }

        for (Employee employee : schedule.getEmployeeList()) {
            makeScheduleEqual();
            fillEmptyDays();
        }
        System.out.println("\tAfter optimalization:");
        for (Employee employee : schedule.getEmployeeList()) {
            System.out.println(employee.getName() + " days left: " + employee.getDaysLeft());
        }

        schedule.setDictionary(realSchedule);


        return schedule;
    }

    public void makeScheduleEqual() {
        Employee empl_maxDaysLeft = null;
        Employee empl_minDaysLeft = null;
        int max = 0;
        int min = 40;
        for (Employee employee : schedule.getEmployeeList()) {
            if (employee.getDaysLeft() < min) {
                min = employee.getDaysLeft();
                empl_minDaysLeft = employee;
            }
            if (employee.getDaysLeft() > max) {
                max = employee.getDaysLeft();
                empl_maxDaysLeft = employee;
            }
        }
        System.out.println("Most days left: " + empl_maxDaysLeft.getName());
        System.out.println("Least days left: " + empl_minDaysLeft.getName());

        for (int day_max : empl_maxDaysLeft.getAvailability()) {
            for (int day_min : empl_minDaysLeft.getAvailability()) {
                if (day_max == day_min) {
                    if (empl_maxDaysLeft.getDaysLeft() >= empl_minDaysLeft.getDaysLeft()) {
                        if (realSchedule.get(day_max).getName().equals(empl_minDaysLeft.getName())) {
                            realSchedule.replace(day_max, empl_maxDaysLeft);
                            decrementDaysLeft(empl_maxDaysLeft);
                            incrementDaysLeft(empl_minDaysLeft);
                        }

                    }
                }
            }
        }
    }

    public void createMap() {
        for (Integer i = 1; i <= schedule.getDaysInMonth(); i++) {
            peopleForDay.put(i, 0);
            realSchedule.put(i, new Employee());
        }
    }

    public void countDaysWithoutPeople() {
        for (Integer i = 1; i <= schedule.getDaysInMonth(); i++) {
            if (peopleForDay.get(i) == 0) {
                daysWithoutPeople += 1;
            }
        }
    }

    public void findLeastWantedDay() {
        for (Employee employee : schedule.getEmployeeList()) {
            assert employee.getAvailability() != null;
            for (Integer day : employee.getAvailability()) {
                if (peopleForDay.containsKey(day)) {
                    if (realSchedule.get(day).getName() == null) {
                        Integer tmp = peopleForDay.get(day);
                        tmp += 1;
                        peopleForDay.replace(day, tmp);
                    } else {
                        peopleForDay.replace(day, 0);
                    }

                }
            }
        }

        leastWantedDay = 1;

        for (Integer key : peopleForDay.keySet()) {
            if (peopleForDay.get(key) > peopleForDay.get(leastWantedDay)) {
                if (peopleForDay.get(key) != 0) {
                    leastWantedDay = key;
                }
            }
        }
    }

    public void fillEmptyDays() {
        for (int i : realSchedule.keySet()) {
            if (realSchedule.get(i).getName() == null) {
                for (Employee employee : schedule.getEmployeeList()) {
                    if (employee.getAvailability().contains(i)) {
                        if (employee.getDaysLeft() > 0) {
                            realSchedule.replace(i, employee);
                            decrementDaysLeft(employee);
                        }
                    }
                }
            }
        }
    }

    public Employee findLeastAvailableEmployeeThisDay(int day) {
        int min = 100;
        Employee leastEmployee = new Employee();
        for (Employee employee : schedule.getEmployeeList()) {
            if (employee.getAvailability() != null) {
                if (employee.getAvailability().contains(day)) {
                    if (employee.getDaysLeft() < min) {
                        if (employee.getDaysLeft() > 0) {
                            leastEmployee = employee;
                            min = leastEmployee.getAvailability().size();
                        }
                    }
                }

            }
        }
        System.out.println("Least available Employee: " + leastEmployee.getName() + " for day " + day);
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
            System.out.println(employee.getName() + " set working days " + employee.getDaysLeft());
        }
    }

    public void decrementDaysLeft(Employee employee) {
        for (Employee employee1 : schedule.getEmployeeList()) {
            if (employee1.getName().equals(employee.getName())) {
                employee1.decrementDaysLeft();
            }
        }
    }

    public void incrementDaysLeft(Employee employee) {
        for (Employee employee1 : schedule.getEmployeeList()) {
            if (employee1.getName().equals(employee.getName())) {
                employee1.incrementDaysLeft();
            }
        }
    }
}
