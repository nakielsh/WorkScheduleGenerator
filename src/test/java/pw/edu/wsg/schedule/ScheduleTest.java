package pw.edu.wsg.schedule;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    @org.junit.jupiter.api.Test
    void setDaysInMonth() {
        //given
        Schedule schedule = new Schedule();
        schedule.setMonth(4);
        schedule.setYear(2021);
        //when
        schedule.setDaysInMonth();
        //then
        assertEquals(30, schedule.getDaysInMonth());
    }
}