package pw.edu.wsg.schedule.algorithms;

import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.schedule.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOEMul {

    private final Schedule schedule;
    private int leastWantedDay;
    private Employee leastAvailableEmployee;

    private final Map<Integer, Integer> peopleForDay = new HashMap<>();
    private final Map<Integer, Integer> peopleWorkingADay = new HashMap<>();
    private final Map<Integer, List<Employee>> realSchedule = new HashMap<>();

    private int maxWorkingDays;
    private int daysWithoutPeople = 0;

    public BOEMul(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule generateSchedule(){
        createMap();
        countMaxWorkingDays();
        setAllDaysLeft();
        findLeastWantedDay();
        countDaysWithoutPeople();

        for (int i = 1; i <= 4*(schedule.getDaysInMonth() - daysWithoutPeople); i++) {
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

        for (Employee employee : schedule.getEmployeeList()) {
            fillEmptyDays();
//            makeScheduleEqual();
        }
        updatePeopleForDay();

        setNumberOfDays();

        schedule.setRealScheduleMulti(realSchedule);

        System.out.println(realSchedule);

        return schedule;
    }



    public void findLeastWantedDay(){
        for (Employee employee : schedule.getEmployeeList()) {
            assert employee.getAvailability() != null;
            for (Integer day : employee.getAvailability()) {
                if (peopleForDay.containsKey(day)) {
                    if (realSchedule.get(day).size() != 2){
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

        for (int key : peopleForDay.keySet()) {
            if (peopleForDay.get(key) > peopleForDay.get(leastWantedDay)) {
                if (peopleForDay.get(key) != 0) {
                    leastWantedDay = key;
                }
            }
        }
    }

    public Employee findLeastAvailableEmployeeThisDay(int day){
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



    public void createMap() {
        for (int i = 1; i <= schedule.getDaysInMonth(); i++) {
            peopleForDay.put(i, 0);
            peopleWorkingADay.put(i,0);
            List<Employee> emptyList = new ArrayList<>();
            realSchedule.put(i, emptyList);
        }
    }

    public void countDaysWithoutPeople() {
        for (int i = 1; i <= schedule.getDaysInMonth(); i++) {
            if (peopleForDay.get(i) == 0) {
                daysWithoutPeople += 1;
            }
        }
    }


    public void setEmployeeToDay(int day, Employee employee){
        updatePeopleForDay();
        if ( peopleWorkingADay.get(day) < 2 ){
            if ( !realSchedule.get(day).contains(employee)){
                List<Employee> toReplaceList = realSchedule.get(day);
                decrementDaysLeft(employee);
                toReplaceList.add(employee);
                realSchedule.replace(day, toReplaceList);
                peopleWorkingADay.put(day, realSchedule.get(day).size());
                System.out.println("Employee: " + employee.getName() + "set to day: " + day);
            }

        }

    }


    public void countMaxWorkingDays() {
        maxWorkingDays = (2*schedule.getDaysInMonth() / schedule.getEmployeeList().size());
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

    public void removeEmptyEmployees(){
        Employee emptyEmployee = null;
        for ( int i : realSchedule.keySet()){
            for (Employee employee : realSchedule.get(i)){
                if( employee.getName().equals("")){
                    emptyEmployee = employee;
                }
            }
            List<Employee> employeeList = realSchedule.get(i);
            if(emptyEmployee != null) {
                employeeList.remove(emptyEmployee);
                realSchedule.replace(i,employeeList);
                peopleWorkingADay.put(i, realSchedule.get(i).size());
                System.out.println("We have an impostor in day: " + i);
            }
        }
    }

    public void updatePeopleForDay(){
        for ( int i : realSchedule.keySet()) {
            peopleWorkingADay.put(i, realSchedule.get(i).size());
        }
    }

    public void fillEmptyDays(){
        removeEmptyEmployees();
        for (int i : realSchedule.keySet()){
            if(realSchedule.get(i).size() < 2 ){
                int daysLeft = -100;
                Employee toReplace = new Employee("");
                for (Employee employee : schedule.getEmployeeList()) {
                    assert employee.getAvailability() != null;
                    if (employee.getAvailability().contains(i)) {
                        if(!realSchedule.get(i).contains(employee)){
                            if (employee.getDaysLeft() != null && employee.getDaysLeft() > daysLeft){
                                daysLeft = employee.getDaysLeft();
                                toReplace = employee;
                            }
                        }

//                        if (employee.getDaysLeft() > 0) {

//                        }
                    }
                }
                System.out.println("Set Employee: " + toReplace.getName() + "to day: " + i);
                setEmployeeToDay(i, toReplace);
            }
        }
    }
}
