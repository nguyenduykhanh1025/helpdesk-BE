package com.backend.helpdesk.common;

import com.backend.helpdesk.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class CommonMethods {

    @Autowired
    private HolidayService holidayService;

    public boolean isEmailNovaHub(String email) {
        String query = "[a-z][a-z0-9_\\.]{5,32}@novahub.vn";

        return Pattern.matches(query, email);
    }

    public boolean isContainsHoliday(List<Date> dates, Calendar calendar) {
        for (Date date : dates) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (cal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && cal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                    && cal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                return false;
            }
        }
        return true;
    }

    public float calculateDaysBetweenTwoDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        float numberOfDays = 0;
        int year = cal1.get(Calendar.YEAR);
        List<Date> dates = new ArrayList<>();
        dates = holidayService.getHolidayThisYear(cal1.get(Calendar.YEAR));
        while (cal1.before(cal2)) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                    && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK)) && isContainsHoliday(dates, cal1)) {
                numberOfDays++;
            }
            cal1.add(Calendar.DATE, 1);
        }
        if (cal1.get(Calendar.HOUR) == 12 && cal2.get(Calendar.HOUR) == 12
                && cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH)) {
            numberOfDays--;
        } else if (cal1.get(Calendar.HOUR) == 8 && cal2.get(Calendar.HOUR) == 12
                || cal1.get(Calendar.HOUR) == 12 && cal2.get(Calendar.HOUR) == 18) {
            numberOfDays = numberOfDays - 0.5f;
        }
        return numberOfDays;
    }

    public List<Date> getListDateBetweenTwoDate(Date date1, Date date2) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date1);
        while (calendar.getTime().before(date2)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        dates.add(date2);
        return dates;
    }
}
