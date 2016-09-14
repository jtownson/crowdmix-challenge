package net.jtownson.jparsec.output;

import net.jtownson.jparsec.messaging.services.Message;
import org.junit.Test;

import static com.googlecode.totallylazy.collections.PersistentList.constructors.list;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OutputFormattingTest {

    @Test
    public void givenARecentWallMessageShouldFormatCorrectly() {

        // given
        Message message = new Message("Alice", "I love the weather today", 0L);
        OutputFormatting outputFormatting = formattingWithFixedTime(2000L);

        // when
        String formattedMessage = outputFormatting.wallMessages(list(message));

        // then
        assertThat(formattedMessage, is("Alice - I love the weather today (seconds ago)\n"));
    }

    @Test
    public void givenARecentTimelineMessageShouldFormatCorrectly() {

        // given
        Message message = new Message("Alice", "I love the weather today", 0L);
        OutputFormatting outputFormatting = formattingWithFixedTime(2000L);

        // when
        String formattedMessage = outputFormatting.timelineMessages(list(message));

        // then
        assertThat(formattedMessage, is("I love the weather today (seconds ago)\n"));
    }

    @Test
    public void givenAMessageThatIs45SecsOldShouldFormatAgeCorrectly() {

        // given
        Message message = new Message("Alice", "I love the weather today", 0L);
        OutputFormatting outputFormatting = formattingWithFixedTime(45000L);

        // when
        String formattedMessage = outputFormatting.wallMessages(list(message));

        // then
        assertThat(formattedMessage, is("Alice - I love the weather today (seconds ago)\n"));
    }

    @Test
    public void givenAMessageThatIs5MinutesOldShouldFormatAgeCorrectly() {

        // given
        Message message = new Message("Alice", "I love the weather today", 0L);
        OutputFormatting outputFormatting = formattingWithFixedTime(5 * 60000L + 10000);

        // when
        String formattedMessage = outputFormatting.wallMessages(list(message));

        // then
        assertThat(formattedMessage, is("Alice - I love the weather today (5 minutes ago)\n"));
    }

    private OutputFormatting formattingWithFixedTime(Long millis) {
        return new OutputFormatting(new ElapsedTime(() -> millis));
    }
}