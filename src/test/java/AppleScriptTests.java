import com.github.pireba.applescript.AppleScript;
import com.github.pireba.applescript.AppleScriptException;
import com.github.pireba.applescript.AppleScriptObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class AppleScriptTests {
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
                        """.formatted("Movies");
        AppleScript as = new AppleScript(s);

        AppleScriptObject o;

        try {
            o = as.executeAsObject();
        }
        catch (AppleScriptException e) {
            fail("should not throw");
        }

        return;
    }
}