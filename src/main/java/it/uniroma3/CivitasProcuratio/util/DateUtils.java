package it.uniroma3.CivitasProcuratio.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static int ageCalculator(Date dateOfBirth) {

        long timestamp = dateOfBirth.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);

        Calendar cal2 = Calendar.getInstance();
        long timestampAct = new Date().getTime();
        cal2.setTimeInMillis(timestampAct);

        return (cal2.get(Calendar.YEAR) - cal.get(Calendar.YEAR));
    }

    public static boolean dateValidation(Date date) {
        if (date.after(Calendar.getInstance().getTime()))
            return false;
        else
            return true;
    }

}
