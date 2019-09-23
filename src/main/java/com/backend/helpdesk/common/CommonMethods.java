package com.backend.helpdesk.common;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class CommonMethods {

    public static boolean isEmailNovaHub(String email) {
        String query = "[a-z][a-z0-9_\\.]{5,32}@novahub.vn";

        return Pattern.matches(query, email);
    }
    public static float calculateDaysBetweenTwoDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        float numberOfDays = 0;
        while (cal1.before(cal2)) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                    && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                numberOfDays++;
            }
            cal1.add(Calendar.DATE, 1);
        }
        if (date1.getHours() == 12 && date2.getHours() == 12) {
            numberOfDays--;
        } else if (date1.getHours() == 8 && date2.getHours() == 12 || date1.getHours() == 12 && date2.getHours() == 18) {
            numberOfDays = numberOfDays - 0.5f;
        }
        return numberOfDays;
    }
}
