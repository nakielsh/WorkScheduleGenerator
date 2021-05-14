package pw.edu.wsg.schedule.algorithms;

import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.schedule.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOE2 {

    private final Schedule schedule;
    private int leastWantedDay;
    private Employee leastAvailableEmployee;
    private final Map<Integer, Integer> peopleForDay = new HashMap<>();
    private final Map<Integer, List<Employee>> realSchedule = new HashMap<>();
    private int maxWorkingDays;
    private int daysWithoutPeople = 0;

    public BOE2(Schedule schedule) {
        this.schedule = schedule;
    }


    public Schedule generateSchedule() {

        createMap();
        countMaxWorkingDays();
        setAllDaysLeft();
        findLeastWantedDay();
        countDaysWithoutPeople();

        for (int i = 1; i <= 2*(schedule.getDaysInMonth() - daysWithoutPeople); i++) {
            findLeastWantedDay();
//            System.out.println(peopleForDay);
            leastAvailableEmployee = findLeastAvailableEmployeeThisDay(leastWantedDay);
            setEmployeeToDay(leastWantedDay, leastAvailableEmployee);
            peopleForDay.replace(leastWantedDay, 0);
//            decrementDaysLeft(leastAvailableEmployee);
//            System.out.println("Decremented in generation: " + leastAvailableEmployee.getName());
        }
        System.out.println("\tBefore optimalization:");
        for (Employee employee : schedule.getEmployeeList()) {
            System.out.println(employee.getName() + " days left: " + employee.getDaysLeft());
        }

//        for (Employee employee : schedule.getEmployeeList()) {
//            fillEmptyDays();
//            makeScheduleEqual();
//        }


//        System.out.println("\tAfter optimalization:");
//        for (Employee employee : schedule.getEmployeeList()) {
//            System.out.println(employee.getName() + " days left: " + employee.getDaysLeft());
//        }
        setNumberOfDays();

        schedule.setRealScheduleMulti(realSchedule);

        System.out.println(realSchedule);

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
        if (empl_maxDaysLeft != null && empl_minDaysLeft != null) {
            System.out.println("Most days left: " + empl_maxDaysLeft.getName());
            System.out.println("Least days left: " + empl_minDaysLeft.getName());
        }


        for (Employee employee1 : schedule.getEmployeeList()) {
            for (Employee employee2 : schedule.getEmployeeList()) {
                if (employee1 != employee2) {
                    assert employee1.getAvailability() != null;
                    for (int day_max : employee1.getAvailability()) {
                        assert employee2.getAvailability() != null;
                        for (int day_min : employee2.getAvailability()) {
                            if (day_max == day_min) {
                                if (employee1.getDaysLeft() != null && employee2.getDaysLeft() != null) {
                                    if (employee1.getDaysLeft() >= employee2.getDaysLeft()) {
                                        Employee toDelete = new Employee("");
                                        for(Employee employee : realSchedule.get(day_max)){
                                            if (employee != null && !employee2.getName().equals("")) {
                                                System.out.println("schedule: " + employee);
                                                System.out.println("Employee: " + employee2);
                                                if (employee.getName().equals(employee2.getName())) {
                                                    if(!realSchedule.get(day_max).contains(employee1)) {
                                                        incrementDaysLeft(employee);
                                                        toDelete = employee;
                                                    }
                                                }
                                            }
                                        }

                                        if(!realSchedule.get(day_max).contains(employee1)){
                                            List<Employee> toReplaceList = realSchedule.get(day_max);
                                            toReplaceList.remove(toDelete);
                                            toReplaceList.add(employee1);
                                            realSchedule.replace(day_max, toReplaceList);
                                            decrementDaysLeft(employee1);
                                            incrementDaysLeft(employee2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void createMap() {
        for (Integer i = 1; i <= schedule.getDaysInMonth(); i++) {
            peopleForDay.put(i, 0);
            List<Employee> toAdd = new ArrayList<>();
            toAdd.add(new Employee(""));
            toAdd.add(new Employee(""));
            realSchedule.put(i, toAdd);

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
                    for( Employee empl : realSchedule.get(day) ){
                        if (empl.getName().equals("")) {
                            Integer tmp = peopleForDay.get(day);
                            tmp += 1;
                            peopleForDay.replace(day, tmp);
                        } else {
                            peopleForDay.replace(day, 0);
                        }
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
            Employee toReplace = new Employee("");
            Employee toDelete = new Employee("");
            for( Employee empl : realSchedule.get(i) ){
                if (empl.getName().equals("")) {
                    int daysLeft = -10;

                    for (Employee employee : schedule.getEmployeeList()) {
                        assert employee.getAvailability() != null;
                        if (employee.getAvailability().contains(i)) {
                            if (employee.getDaysLeft() != null && employee.getDaysLeft() > daysLeft) {
                                if(!realSchedule.get(i).contains(toReplace)) {
                                    daysLeft = employee.getDaysLeft();
                                    incrementDaysLeft(empl);
                                    decrementDaysLeft(employee);
                                    toReplace = employee;
                                    toDelete = empl;
                                }

                            }
//
                        }
                    }

                }
            }
            if(!realSchedule.get(i).contains(toReplace)){
                List<Employee> toReplaceList = realSchedule.get(i);
                toReplaceList.remove(toDelete);
                toReplaceList.add(toReplace);
                realSchedule.replace(i, toReplaceList);
            }
        }
    }

    public Employee findLeastAvailableEmployeeThisDay(int day) {
        int min = 200;
        Employee leastEmployee = new Employee("");
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
        maxWorkingDays = (2*schedule.getDaysInMonth() / schedule.getEmployeeList().size());
    }

    public void setEmployeeToDay(int day, Employee employee) {
        List<Employee> toReplaceList = realSchedule.get(day);
        Employee toDelete = new Employee("");
        for(Employee employee1 : toReplaceList){
            if(employee1.getName().equals("")){
                if(!toReplaceList.contains(employee)) {
                    incrementDaysLeft(employee1);
                    toDelete = employee1;
                }

            }
        }
        if(!toReplaceList.contains(employee)){
            toReplaceList.remove(toDelete);
            toReplaceList.add(employee);
            realSchedule.replace(day, toReplaceList);
            decrementDaysLeft(employee);
            System.out.println("Decremented in set: " + employee.getName());
        }


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
                System.out.println("Decremented day for: " + employee.getName());
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

    public void setNumberOfDays() {
        for (Employee employee : schedule.getEmployeeList()) {
            employee.setNumOfWorkingDays(maxWorkingDays - employee.getDaysLeft());
        }
    }
}
