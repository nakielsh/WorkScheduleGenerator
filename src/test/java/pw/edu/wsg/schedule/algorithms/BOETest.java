package pw.edu.wsg.schedule.algorithms;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import pw.edu.wsg.employee.Employee;
import pw.edu.wsg.schedule.Schedule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BOETest {

//    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
//    private final ByteArrayOutputStream err = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;
//    private final PrintStream originalErr = System.err;
//
//    @Before
//    public void setStreams() {
//        System.setOut(new PrintStream(out));
//        System.setErr(new PrintStream(err));
//    }
//
//    @After
//    public void restoreInitialStreams() {
//        System.setOut(originalOut);
//        System.setErr(originalErr);
//    }

    @Test
    void generateSchedule() {
        //given
        Schedule schedule = new Schedule();
        schedule.setYear(2020);
        schedule.setMonth(3);
        schedule.setDaysInMonth();

        List<Integer> avLiska = new ArrayList<>();
        avLiska.add(1);
        avLiska.add(2);
        avLiska.add(3);
        avLiska.add(5);
        avLiska.add(6);
        avLiska.add(8);
        avLiska.add(10);
        avLiska.add(13);
        avLiska.add(15);
        avLiska.add(18);

        List<Integer> avHuberta = new ArrayList<>();
        avLiska.add(1);
        avLiska.add(2);
        avLiska.add(4);
        avLiska.add(6);
        avLiska.add(11);
        avLiska.add(12);
        avLiska.add(15);
        avLiska.add(17);
        avLiska.add(19);
        avLiska.add(20);

        List<Integer> avMarty = new ArrayList<>();
        avLiska.add(2);
        avLiska.add(3);
        avLiska.add(5);
        avLiska.add(8);
        avLiska.add(10);
        avLiska.add(16);
        avLiska.add(19);
        avLiska.add(21);
        avLiska.add(22);
        avLiska.add(25);

        Employee lisek = new Employee("Lisek", avLiska );
        schedule.addToEmployeeList(lisek);

        Employee Olek = new Employee("Olek", avHuberta );
        schedule.addToEmployeeList(Olek);

        Employee Marta = new Employee("Marta", avMarty );
        schedule.addToEmployeeList(Marta);

        //when
        BOE boe = new BOE(schedule);
        Schedule generatedSchedule = boe.generateSchedule();
        System.out.print(generatedSchedule.getDictionary());

        //then
    }
}