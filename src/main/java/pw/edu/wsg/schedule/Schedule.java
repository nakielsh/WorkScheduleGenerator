package pw.edu.wsg.schedule;

import pw.edu.wsg.employee.Employee;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {

    private Map<Integer, Employee> realSchedule1;
    private Map<Integer, List<Employee>> realScheduleMulti;
    private List<Employee> employeeList = new ArrayList<>();
    private Integer year;
    private String monthName;
    private Integer month;
    private int daysInMonth;

    private int maxPeopleForDay;

    public Schedule() {
    }

    public void setDaysInMonth() {
        YearMonth yearMonth = YearMonth.of(getYear(), getMonth());
        this.daysInMonth = yearMonth.lengthOfMonth();
    }

    public Map<Integer, Employee> getRealSchedule1() {
        return realSchedule1;
    }

    public void setRealSchedule1(Map<Integer, Employee> dictionary) {
        this.realSchedule1 = dictionary;
    }

    public Map<Integer, List<Employee>> getRealScheduleMulti() {
        return realScheduleMulti;
    }

    public void setRealScheduleMulti(Map<Integer, List<Employee>> realScheduleMulti) {
        this.realScheduleMulti = realScheduleMulti;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        setMonthName();
    }

    public void setMaxPeopleForDay(int maxPeopleForDay) {
        this.maxPeopleForDay = maxPeopleForDay;
    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public int addToEmployeeList(Employee employee) {
        if (employeeList.contains(employee)) {
            return 0;
        } else {
            employeeList.add(employee);
            return 1;
        }
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(){
        switch(month) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;

        }
    }

    public Map<Integer, List<String>> findEmptyDaysInSchedule1(){
        Map<Integer, List<String> >  emptyDaysWithPossibleEmployees = new HashMap<>();

        for (int i : realSchedule1.keySet()) {
            if (realSchedule1.get(i).getName().equals("")) {
                List<String> possibleEmployees = new ArrayList<>();
                for (Employee employee : employeeList) {
                    if (employee.getAvailability().contains(i)) {
                        possibleEmployees.add(employee.getName());
                    }
                }
                if (possibleEmployees.size() == 0 ){
                    possibleEmployees = null;
                }
                emptyDaysWithPossibleEmployees.put(i, possibleEmployees);
            }
        }
        return emptyDaysWithPossibleEmployees;
    }


    public Map<Integer, List<String>> findEmptyDaysInScheduleMulti(){
        Map<Integer, List<String> >  emptyDaysWithPossibleEmployees = new HashMap<>();

        for (int i : realScheduleMulti.keySet()) {
                if (realScheduleMulti.get(i).size() < maxPeopleForDay) {
                    List<String> possibleEmployees = new ArrayList<>();
                    for (Employee employee1 : employeeList) {
                        if (employee1.getAvailability().contains(i)) {
                            possibleEmployees.add(employee1.getName());
                        }
                    }
                    if (possibleEmployees.size() == 0 ){
                        possibleEmployees = null;
                    }
                    emptyDaysWithPossibleEmployees.put(i, possibleEmployees);
                }
            }
        return emptyDaysWithPossibleEmployees;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "dictionary=" + realSchedule1 +
                ", employeeList=" + employeeList +
                ", year=" + year +
                ", month=" + month +
                ", daysInMonth=" + daysInMonth +
                '}';
    }
}
