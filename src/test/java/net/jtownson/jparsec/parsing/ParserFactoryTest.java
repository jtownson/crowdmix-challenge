package net.jtownson.jparsec.parsing;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ParserFactoryTest {

    private ParserFactory parserFactory = new ParserFactory();

    /*
     * sunny day scenarios
     */

    @Test
    public void givenAValidPostCommandShouldParseIt() {

        // given
        String command = "Charlie -> I'm in New York today!";

        // when
        Commands.Command postCommand = parserFactory.parse(command);

        // then
        assertThat(postCommand, is(new Commands.PostCommand("Charlie", "I'm in New York today!")));
    }

    @Test
    public void givenAValidFollowsCommandShouldParseIt() {

        // given
        String command = "Charlie follows Alice";

        // when
        Commands.Command followsCommand = parserFactory.parse(command);

        // then
        assertThat(followsCommand, is(new Commands.FollowsCommand("Charlie", "Alice")));
    }

    @Test
    public void givenAValidWallCommandShouldParseIt() {

        // given
        String command = "Charlie wall";

        // when
        Commands.Command wallCommand = parserFactory.parse(command);

        // then
        assertThat(wallCommand, is(new Commands.WallCommand("Charlie")));
    }

    @Test
    public void givenAValidReadCommandShouldParseIt() {

        // given
        String command = "Alice";

        // when
        Commands.Command readCommand = parserFactory.parse(command);

        // then
        assertThat(readCommand, is(new Commands.ReadCommand("Alice")));
    }

    /*
     * some other scenarios worth thinking about
     */

    @Test
    public void givenAPostCommandWithIgnorableWhitespaceShouldIgnoreIt() {

        // given
        String command = "  \tCharlie ->  I'm in New York today! ";

        // when
        Commands.Command postCommand = parserFactory.parse(command);

        // then
        assertThat(postCommand, is(new Commands.PostCommand("Charlie", "I'm in New York today!")));
    }

    @Test
    public void givenAPostCommandWhereTheMessageContainsThePostOperatorShouldParseIt() {

        // given
        String command = "Charlie -> he uses funny characters like ->";

        // when
        Commands.Command postCommand = parserFactory.parse(command);

        // then
        assertThat(postCommand, is(new Commands.PostCommand("Charlie", "he uses funny characters like ->")));
    }

    @Test
    public void givenAWallCommandWhereTheUserNameIsWallShouldParseIt() {

        // given
        String command = "wall wall";

        // when
        Commands.Command wallCommand = parserFactory.parse(command);

        // then
        assertThat(wallCommand, is(new Commands.WallCommand("wall")));
    }

    @Test
    public void givenAUserNameWithNumbersInReadCommandShouldParse() {

        // given
        String command = "Alice001";

        // when
        Commands.Command readCommand = parserFactory.parse(command);

        // then
        assertThat(readCommand, is(new Commands.ReadCommand("Alice001")));
    }
}