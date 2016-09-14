package net.jtownson.jparsec.messaging.patch;

import com.fiftyonred.mock_jedis.MockJedis;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MockJedisPool extends JedisPool {
    private MockJedis client = null;

    public MockJedisPool() {
        super(new GenericObjectPoolConfig(), "localhost");
    }

    @Override
    public Jedis getResource() {
        if (client == null) {
            client = new PatchedMockJedis("localhost");
        }
        return client;
    }

    @Override
    public void returnResource(final Jedis resource) {

    }

    @Override
    public void returnBrokenResource(final Jedis resource) {

    }
}