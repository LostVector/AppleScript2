import com.github.pireba.applescript.AppleScript;
import com.github.pireba.applescript.AppleScriptException;
import com.github.pireba.applescript.AppleScriptList;
import com.github.pireba.applescript.AppleScriptObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}