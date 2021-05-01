package pw.edu.wsg.schedule;

import pw.edu.wsg.employee.Employee;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Schedule {

    private Map<Integer, Employee> dictionary;
    private List<Employee> employeeList = new ArrayList<>();
    private Integer year;
    private Integer month;
    private int daysInMonth;

    public Schedule() {
    }

    public void setDaysInMonth() {
        YearMonth yearMonth = YearMonth.of(getYear(), getMonth());
        this.daysInMonth = yearMonth.lengthOfMonth();
    }

    public Map<Integer, Employee> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<Integer, Employee> dictionary) {
        this.dictionary = dictionary;
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

    @Override
    public String toString() {
        return "Schedule{" +
                "dictionary=" + dictionary +
                ", employeeList=" + employeeList +
                ", year=" + year +
                ", month=" + month +
                ", daysInMonth=" + daysInMonth +
                '}';
    }
}
