package Test;

import org.junit.Test;
import testex.DateFormatter;
import testex.JokeException;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DateFormatterTest {

    @Test
    public void testFormattedDate() throws Exception {
        String expected = "13 Mar 2017 01:58 PM";
        Date time = new Date(1489406307212L);
        DateFormatter dateFormatter = new DateFormatter();
        String formattedDate = dateFormatter.getFormattedDate("Europe/Kiev", time);
        assertThat(formattedDate, equalTo(expected));
    }

    @Test(expected = JokeException.class)
    public void testFormattedDateException() throws Exception {
        Date time = new Date();
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.getFormattedDate("Unknown", time);
    }

}