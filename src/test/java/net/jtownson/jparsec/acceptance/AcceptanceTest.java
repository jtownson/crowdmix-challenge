package net.jtownson.jparsec.acceptance;

import net.jtownson.jparsec.Pipeline;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static net.jtownson.jparsec.Context.inApplicationContext;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Prove scenarios outlined in the challenge description
 */
public class AcceptanceTest {

    String input =
            "Alice -> I love the weather today\n" +
            "Bob -> Damn! We lost!\n" +
            "Bob -> Good game though.\n" +
            "Alice\n" +
            "Bob\n" +
            "Charlie -> I'm in New York today! Anyone wants to have a coffee?\n" +
            "Charlie follows Alice\n" +
            "Charlie wall\n" +
            "Charlie follows Bob\n" +
            "Charlie wall\n";

    String expectedOutput =
            "> " + /* Alice -> I love the weather today */
            "> " + /* Bob -> Damn! We lost! */
            "> " + /* Bob -> Good game though. */
            "> " + /* Alice */
            "I love the weather today (seconds ago)\n" +
            "> " + /* Bob */
            "Good game though. (seconds ago)\n" +
            "Damn! We lost! (seconds ago)\n" +
            "> " + /* Charlie -> I'm in New York today! Anyone wants to have a coffee? */
            "> " + /* Charlie follows Alice */
            "> " + /* Charlie wall */
            "Charlie - I'm in New York today! Anyone wants to have a coffee? (seconds ago)\n" +
            "Alice - I love the weather today (seconds ago)\n" +
            "> " + /* Charlie follows Bob */
            "> " + /* Charlie wall */
            "Charlie - I'm in New York today! Anyone wants to have a coffee? (seconds ago)\n" +
            "Bob - Good game though. (seconds ago)\n" +
            "Bob - Damn! We lost! (seconds ago)\n" +
            "Alice - I love the weather today (seconds ago)\n" +
            "> "; /* EOF */

    @Test
    public void replayedSessionShouldGenerateAcceptableResponses() {

        // given
        StringReader sessionInput = new StringReader(input);
        StringWriter sessionOutput = new StringWriter();

        // when
        runningASession(sessionInput, sessionOutput);

        // then
        assertThat(sessionOutput.toString(), is(expectedOutput));
    }

    private void runningASession(StringReader sessionInput, StringWriter sessionOutput) {
        inApplicationContext(

                wiring -> {

                    Pipeline pipeline = new Pipeline(wiring.getParserFactory(), wiring.getMessagingFacade());

                    pipeline.process(new BufferedReader(sessionInput), new PrintWriter(sessionOutput));
                }
        );
    }
}
