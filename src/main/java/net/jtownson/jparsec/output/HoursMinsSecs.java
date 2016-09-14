package net.jtownson.jparsec.output;

import com.googlecode.totallylazy.Triple;

import static com.googlecode.totallylazy.Functions.returns;

/**
 * Hours, minutes, seconds.
 */
public class HoursMinsSecs extends Triple<Long, Long, Long> {

    public HoursMinsSecs(Long hours, Long minutes, Long seconds) {
        super(returns(hours), returns(minutes), returns(seconds));
    }

    public Long hours() {
        return first();
    }
    public Long minutes() {
        return second();
    }
    public Long seconds() {
        return third();
    }
}
