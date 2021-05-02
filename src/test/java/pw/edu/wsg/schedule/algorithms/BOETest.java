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

        List<Integer> avOlka = new ArrayList<>();
        avOlka.add(1);
        avOlka.add(2);
        avOlka.add(4);
        avOlka.add(6);
        avOlka.add(11);
        avOlka.add(12);
        avOlka.add(15);
        avOlka.add(17);
        avOlka.add(19);
        avOlka.add(20);

        List<Integer> avMarty = new ArrayList<>();
        avMarty.add(2);
        avMarty.add(3);
        avMarty.add(5);
        avMarty.add(8);
        avMarty.add(10);
        avMarty.add(16);
        avMarty.add(19);
        avMarty.add(21);
        avMarty.add(22);
        avMarty.add(25);

        Employee lisek = new Employee("Lisek", avLiska);
        schedule.addToEmployeeList(lisek);

        Employee Olek = new Employee("Olek", avOlka);
        schedule.addToEmployeeList(Olek);

        Employee Marta = new Employee("Marta", avMarty);
        schedule.addToEmployeeList(Marta);

        //when
        BOE boe = new BOE(schedule);
        Schedule generatedSchedule = boe.generateSchedule();
        System.out.print(generatedSchedule.getDictionary());
        System.out.println("\ngenerated schedule" + generatedSchedule.getEmployeeList().toString());
        System.out.println("previous schedule" + schedule.getEmployeeList().toString());
        int nLisek = 0;
        int nOlek = 0;
        int nMarta = 0;
        int none = 0;

        for (Employee employee : generatedSchedule.getDictionary().values()) {
            if (employee.getName() != null) {
                if (employee.getName().equals("Lisek")) {
                    nLisek += 1;
                } else if (employee.getName().equals("Olek")) {
                    nOlek += 1;
                } else if (employee.getName().equals("Marta")) {
                    nMarta += 1;
                }
            } else {
                none += 1;
            }

        }

        System.out.println("\nLisek: " + nLisek);
        System.out.println("Olek: " + nOlek);
        System.out.println("Marta: " + nMarta);
        System.out.println("None: " + none);

        System.out.println("Last days: ");
        System.out.println(generatedSchedule.getEmployeeList().toString());


        //then
    }
}