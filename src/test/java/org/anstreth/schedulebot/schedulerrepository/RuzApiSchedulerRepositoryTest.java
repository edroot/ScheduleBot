package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.ruzapi.ruzapirepository.WeekScheduleRepository;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RuzApiSchedulerRepositoryTest {
    @InjectMocks
    private RuzApiSchedulerRepository ruzApiSchedulerRepository;

    @Mock
    private WeekScheduleRepository weekScheduleRepository;

    @Test(expected = NoScheduleForDay.class)
    public void ifThereAreNoScheduleForPassedDateExceptionIsThrown() throws Exception {
        int groupId = 1;
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        WeekSchedule weekSchedule = getWeekScheduleWithDays();
        when(weekScheduleRepository.getWeekScheduleForGroupForDate(groupId, date)).thenReturn(weekSchedule);

        ruzApiSchedulerRepository.getScheduleForGroupForDay(groupId, date);
    }

    @Test
    public void dayIsFoundByItsWeekdayField() throws Exception {
        int groupId = 1;
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        Day dayWithNumber = getDayWithDayWeek(2);
        WeekSchedule weekSchedule = getWeekScheduleWithDays(dayWithNumber);
        when(weekScheduleRepository.getWeekScheduleForGroupForDate(groupId, date)).thenReturn(weekSchedule);

        Day returnedDay = ruzApiSchedulerRepository.getScheduleForGroupForDay(groupId, date);

        assertThat(returnedDay, is(dayWithNumber));
    }

    private WeekSchedule getWeekScheduleWithDays(Day ...days) {
        WeekSchedule weekSchedule = new WeekSchedule();
        weekSchedule.setDays(Arrays.asList(days));
        return weekSchedule;
    }

    private Day getDayWithDayWeek(int weekDay) {
        Day dayWithNumber = new Day();
        dayWithNumber.setWeekDay(weekDay);
        return dayWithNumber;
    }
}