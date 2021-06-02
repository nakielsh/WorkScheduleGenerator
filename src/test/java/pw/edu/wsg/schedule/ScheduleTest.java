package pw.edu.wsg.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pw.edu.wsg.employee.Employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ScheduleTest {

    @Test
    void shouldCountProperNumberOfDays() {
        //given
        Schedule schedule = new Schedule();
        schedule.setMonth(4);
        schedule.setYear(2021);

        //when
        schedule.setDaysInMonth();

        //then
        assertEquals(30, schedule.getDaysInMonth());
    }

    @Test
    void shouldReturn0WhenAddingTheSameEmployeeToTheList() {
        //given
        Schedule schedule = new Schedule();
        schedule.setMonth(4);
        schedule.setYear(2021);

        //when
        Employee employee = new Employee("test");
        schedule.addToEmployeeList(employee);
        int ret = schedule.addToEmployeeList(employee);
        int size = schedule.getEmployeeList().size();

        //then
        assertEquals(0, ret);
        assertEquals(1, size);
    }

    @Test
    void shouldReturnProperMonthName() {
        //given
        Schedule schedule = new Schedule();
        schedule.setMonth(4);
        schedule.setYear(2021);

        //when
        String month = schedule.getMonthName();

        //then
        assertEquals("April", month);
    }


}