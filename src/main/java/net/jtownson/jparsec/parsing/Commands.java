package net.jtownson.jparsec.parsing;

import com.googlecode.totallylazy.Pair;

import static com.googlecode.totallylazy.Callables.returns;

/**
 * Command objects representing the message reading/posting options available to a user.
 *
 * I have chosen to extend a generic tuple class to inherit the equals, hashcode and toString boilerplate
 * (the source file is three times larger when these methods live in each command class)
 */
public class Commands {

    public interface Command {

    }

    public static final class WallCommand extends Pair<String, String> implements Command {

        public WallCommand(String userName) {
            super(returns(userName), returns("wall"));
        }

        public String getUserName() {
            return first();
        }
    }

    public static final class ReadCommand extends Pair<String, String> implements Command {

        public ReadCommand(String userName) {
            super(returns(userName), returns("read"));
        }

        public String getUserName() {
            return first();
        }
    }

    public static final class PostCommand extends Pair<String, String> implements Command {

        public PostCommand(String userName, String body) {
            super(returns(userName), returns(body));
        }

        public String getUserName() {
            return first();
        }

        public String getBody() {
            return second();
        }
    }

    public static final class FollowsCommand extends Pair<String, String> implements Command {

        public FollowsCommand(String subjectUserName, String objectUserName) {
            super(returns(subjectUserName), returns(objectUserName));
        }

        public String getSubjectUserName() {
            return first();
        }

        public String getObjectUserName() {
            return second();
        }
    }

    public static final class SkipCommand implements Command {}
}
