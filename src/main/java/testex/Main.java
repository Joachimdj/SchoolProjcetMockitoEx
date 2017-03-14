package testex;

import java.util.Date;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) throws JokeException {

        for (String str : TimeZone.getAvailableIDs()) {
            System.out.println(str);
        }

        Date date = new Date();

        //Executing our public method with a valid String:
        System.out.println(new DateFormatter().getFormattedDate("Europe/Kiev", date));
        System.out.println(new DateFormatter().getFormattedDate("ImNotLegal", date));
    }

}
