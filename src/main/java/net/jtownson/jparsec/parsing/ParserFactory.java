package net.jtownson.jparsec.parsing;

import net.jtownson.jparsec.parsing.Commands.Command;
import net.jtownson.jparsec.parsing.Commands.SkipCommand;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.pattern.CharPredicate;

import static org.codehaus.jparsec.Scanners.*;
import static org.codehaus.jparsec.functors.Maps.identity;

/**
 * Read user messaging commands into command objects.
 */
public class ParserFactory {

    static final CharPredicate ANY_CHAR = c -> true;

    static final Parser<Void> postOperator = WHITESPACES.next(Scanners.string("->"));
    static final Parser<Void> followsOperator = WHITESPACES.next(Scanners.string("follows"));
    static final Parser<Void> wallOperator = WHITESPACES.next(Scanners.string("wall"));

    static final Parser<String> userName = WHITESPACES.optional().next(IDENTIFIER);
    static final Parser<String> message = WHITESPACES.optional().next(many(ANY_CHAR).source());

    public Command parse(String command) {
        return Parsers.or(
                postCommand(),
                followsCommand(),
                wallCommand(),
                readCommand(),
                skipCommand())
                .map(identity()).parse(command);
    }

    // Charlie -> I'm in New York today!
    private Parser<Command> postCommand() {
        return Parsers.tuple(userName, postOperator, message).map(t -> new Commands.PostCommand(t.a, t.c.trim()));
    }

    // Charlie follows Alice
    private Parser<Command> followsCommand() {
        return Parsers.tuple(userName, followsOperator, userName).map(t -> new Commands.FollowsCommand(t.a, t.c));
    }

    // Charlie wall
    private Parser<Command> wallCommand() {
        return Parsers.tuple(userName, wallOperator).map(t -> new Commands.WallCommand(t.a));
    }

    // Alice
    private Parser<Command> readCommand() {
        return IDENTIFIER.map(Commands.ReadCommand::new);
    }

    private Parser<Command> skipCommand() {
        return WHITESPACES.optional().map(a -> new SkipCommand());
    }
}
