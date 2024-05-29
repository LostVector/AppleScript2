import com.github.pireba.applescript.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// These tests are TV app specific due to my use case.
// they need to be adapted to something that will test universally on all macOS systems.
public class AppleScriptTests {

    // empty responses should not exception
    @Test
    public void testListEmptyPlaylist() {
        String s = """
                        tell application "TV"
                            set results to (every playlist)
                            repeat with p in results
                                set cur_name to (get name of p)
                                if cur_name is "%s" then
                                    return {id of p}
                                end if
                            end repeat
                        end tell
                        """.formatted("Nonexistent Playlist");
        AppleScript as = new AppleScript(s);

        AppleScriptObject o;

        try {
            o = as.executeAsObject();
            Assertions.assertNull(o);
        }
        catch (AppleScriptException e) {
            fail("should not throw");
        }
    }

    @Test
    public void testListNonEmptyPlaylist() {
        String s = """
                        tell application "TV"
                            set results to (every playlist)
                            repeat with p in results
                                set cur_name to (get name of p)
                                if cur_name is "%s" then
                                    return {id of p}
                                end if
                            end repeat
                        end tell
                        """.formatted("Movies");
        AppleScript as = new AppleScript(s);

        AppleScriptObject o;

        try {
            o = as.executeAsObject();
            AppleScriptList l = o.getList();
            Assertions.assertTrue(l.size() > 0);
        }
        catch (AppleScriptException e) {
            fail("should not throw");
        }
    }

    @Test
    public void testDateFormatEnglishUS() {
        String s = "date \"Saturday, November 27, 2010 at 2:56:31 PM\"";
        AppleScriptObject o = new AppleScriptObject(s);

        try {
            Date d = o.getDate(AppleScriptDateFormat.ENGLISH_US);
            Calendar c = new GregorianCalendar();
            c.setTime(d);

            assertEquals(7, c.get(Calendar.DAY_OF_WEEK));
            assertEquals(10, c.get(Calendar.MONTH));
            assertEquals(27, c.get(Calendar.DAY_OF_MONTH));
            assertEquals(2010, c.get(Calendar.YEAR));

            assertEquals(14, c.get(Calendar.HOUR_OF_DAY));
            assertEquals(56, c.get(Calendar.MINUTE));
            assertEquals(31, c.get(Calendar.SECOND));
        }
        catch (AppleScriptException e) {
            fail("should not throw");
        }
    }
}