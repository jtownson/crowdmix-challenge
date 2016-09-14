package net.jtownson.jparsec.messaging.patch;

import com.fiftyonred.mock_jedis.MockJedis;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class MockJedisLpushTest {

    @Test
    public void lpushShouldPushtoTheFrontOfAList() {

        // given
        MockJedis jedis = new PatchedMockJedis("localhost");
        jedis.lpush("list", "c");
        jedis.lpush("list", "b");
        jedis.lpush("list", "a");

        // when
        List<String> list = jedis.lrange("list", 0, -1);

        // then
        assertThat(list, is(asList("a", "b", "c")));
    }
}
