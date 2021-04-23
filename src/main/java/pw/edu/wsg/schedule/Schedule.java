package pw.edu.wsg.schedule;

import pw.edu.wsg.employee.Employee;

import java.time.Month;
import java.time.Year;
import java.util.Map;

public class Schedule {

    private Map<Integer, Employee> dictionary;
    private Year year;
    private Month month;
    private int daysInMonth;

    public Schedule() {
    }
}
