package pw.edu.wsg.schedule.algorithms;

import org.junit.jupiter.api.Test;
import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.schedule.Schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BOETest {

    List<Integer> allDays = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);


    @Test
    void shouldFillAllDaysWhenGivenFullAvailability() {
        //given
        Schedule schedule = new Schedule();
        schedule.setYear(2020);
        schedule.setMonth(3);
        schedule.setDaysInMonth();

        List<Integer> avLiska = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> avOlka = Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        List<Integer> avMarty = Arrays.asList(21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);

        Employee lisek = new Employee("Lisek", avLiska);
        schedule.addToEmployeeList(lisek);

        Employee Olek = new Employee("Olek", avOlka);
        schedule.addToEmployeeList(Olek);

        Employee Marta = new Employee("Marta", avMarty);
        schedule.addToEmployeeList(Marta);

        //when
        BOE boe = new BOE(schedule);
        Schedule generatedSchedule = boe.generateSchedule();
        int size = generatedSchedule.findEmptyDaysInSchedule1().size();

        //then
        assertEquals(0, size);
    }

    @Test
    void shouldFillNoDaysWhenGivenZeroAvailabilityFor3Employees() {
        //given
        Schedule schedule = new Schedule();
        schedule.setYear(2020);
        schedule.setMonth(3);
        schedule.setDaysInMonth();

        for (int i = 0; i < 3; i++) {
            List<Integer> days = new ArrayList<>();
            Employee employee = new Employee(String.valueOf(i), days);
            schedule.addToEmployeeList(employee);
        }

        //when
        BOE boe = new BOE(schedule);
        Schedule generatedSchedule = boe.generateSchedule();
        int size = generatedSchedule.findEmptyDaysInSchedule1().size();

        //then
        assertEquals(31, size);
    }

    @Test
    void shouldFillAllDaysWhenGivenFullAvailabilityFor3Employees() {
        //given
        Schedule schedule = new Schedule();
        schedule.setYear(2020);
        schedule.setMonth(3);
        schedule.setDaysInMonth();

        for (int i = 0; i < 3; i++) {
            List<Integer> days = allDays;
            Employee employee = new Employee(String.valueOf(i), days);
            schedule.addToEmployeeList(employee);
        }

        //when
        BOE boe = new BOE(schedule);
        Schedule generatedSchedule = boe.generateSchedule();
        int size = generatedSchedule.findEmptyDaysInSchedule1().size();

        //then
        assertEquals(0, size);
    }

    @Test
    void shouldFillAllDaysWhenGivenFullAvailabilityFor10Employees() {
        //given
        Schedule schedule = new Schedule();
        schedule.setYear(2020);
        schedule.setMonth(3);
        schedule.setDaysInMonth();


        for (int i = 0; i < 10; i++) {
            List<Integer> days = allDays;
            Employee employee = new Employee(String.valueOf(i), days);
            schedule.addToEmployeeList(employee);
        }

        //when
        BOE boe = new BOE(schedule);
        Schedule generatedSchedule = boe.generateSchedule();
        int size = generatedSchedule.findEmptyDaysInSchedule1().size();

        //then
        assertEquals(0, size);
    }

    @Test
    void shouldFillNoDayWhenGivenZeroAvailabilityFor10Employees() {
        //given
        Schedule schedule = new Schedule();
        schedule.setYear(2020);
        schedule.setMonth(3);
        schedule.setDaysInMonth();

        for (int i = 0; i < 10; i++) {
            List<Integer> days = new ArrayList<>();
            Employee employee = new Employee(String.valueOf(i), days);
            schedule.addToEmployeeList(employee);
        }

        //when
        BOE boe = new BOE(schedule);
        Schedule generatedSchedule = boe.generateSchedule();
        int size = generatedSchedule.findEmptyDaysInSchedule1().size();

        //then
        assertEquals(31, size);
    }

    @Test
    void shouldProperlyFindLeastWantedDay_10() {
        //given
        Schedule schedule = new Schedule();
        schedule.setYear(2020);
        schedule.setMonth(3);
        schedule.setDaysInMonth();

        for (int i = 0; i < 10; i++) {
            List<Integer> days = allDays;
            Employee employee = new Employee(String.valueOf(i), days);
            schedule.addToEmployeeList(employee);
        }

        List<Integer> days = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
        Employee employee = new Employee("additional", days);
        schedule.addToEmployeeList(employee);

        //when
        BOE boe = new BOE(schedule);
        boe.createMap();
        boe.countMaxWorkingDays();
        boe.setAllDaysLeft();
        int leastWantedDay = boe.findLeastWantedDay();

        //then
        assertEquals(10, leastWantedDay);
    }

    @Test
    void shouldProperlyFindLeastAvailableEmployee_Additional() {
        //given
        Schedule schedule = new Schedule();
        schedule.setYear(2020);
        schedule.setMonth(3);
        schedule.setDaysInMonth();

        for (int i = 0; i < 10; i++) {
            List<Integer> days = allDays;
            Employee employee = new Employee(String.valueOf(i), days);
            schedule.addToEmployeeList(employee);
        }

        List<Integer> days = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
        Employee employee = new Employee("additional", days);
        schedule.addToEmployeeList(employee);

        //when
        BOE boe = new BOE(schedule);
        boe.createMap();
        boe.countMaxWorkingDays();
        boe.setAllDaysLeft();
        String leastAvailableEmployee = boe.findLeastAvailableEmployeeThisDay(2).getName();

        //then
        assertEquals("additional", leastAvailableEmployee);
    }


}