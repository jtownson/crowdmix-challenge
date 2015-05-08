package net.jtownson.crowdmix_challenge.output;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ElapsedTimeTest {

    @Test
    public void givenZeroMillisHMSShouldBeAllZero() {

        // given
        ElapsedTime et = new ElapsedTime(() -> 0L);

        // when
        HoursMinsSecs hms = et.getHMS(0L);

        // then
        assertThat(hms, is(new HoursMinsSecs(0L, 0L, 0L)));
    }

    @Test
    public void givenAFewMillisHMSShouldBeAllZero() {

        // given
        ElapsedTime et = new ElapsedTime(() -> 10L);

        // when
        HoursMinsSecs hms = et.getHMS(0L);

        // then
        assertThat(hms, is(new HoursMinsSecs(0L, 0L, 0L)));
    }

    @Test
    public void given1000MillisHMSShouldBeOneSec() {

        // given
        ElapsedTime et = new ElapsedTime(() -> 1000L);

        // when
        HoursMinsSecs hms = et.getHMS(0L);

        // then
        assertThat(hms, is(new HoursMinsSecs(0L, 0L, 1L)));
    }

    @Test
    public void given5Minutes25SecsHMSShouldBeCorrect() {

        // given
        ElapsedTime et = new ElapsedTime(() -> 5*60000 + 25*1000);

        // when
        HoursMinsSecs hms = et.getHMS(0L);

        // then
        assertThat(hms, is(new HoursMinsSecs(0L, 5L, 25L)));
    }

    @Test
    public void given2Hours5Minutes25SecsHMSShouldBeCorrect() {

        // given
        ElapsedTime et = new ElapsedTime(() -> 2*3600*1000 + 5*60000 + 25*1000);

        // when
        HoursMinsSecs hms = et.getHMS(0L);

        // then
        assertThat(hms, is(new HoursMinsSecs(2L, 5L, 25L)));
    }
}