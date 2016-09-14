package net.jtownson.jparsec.messaging.services;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class RedisMessagingSchema implements MessagingSchema {

    private JedisPool jedisPool;

    public RedisMessagingSchema(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void hmset(String key, Map<String, String> properties) {
        template(jedis -> jedis.hmset(key, properties));
    }

    public List<String> hmget(String key, String... fields) {
        return template(jedis -> jedis.hmget(key, fields));
    }

    public long lpush(String key, String value) {
        return template(jedis -> jedis.lpush(key, value));
    }

    public List<String> lrange(String key, long start, long end) {
        return template(jedis -> jedis.lrange(key, start, end));
    }

    public Set<String> smembers(String key) {
        return template(jedis -> jedis.smembers(key));
    }

    public long sadd(String key, String value) {
        return template(jedis -> jedis.sadd(key, value));
    }

    public long incr(String key) {
        return template(jedis -> jedis.incr(key));
    }

    private <T> T template(Function<Jedis, T> f) {
        try (Jedis jedis = jedisPool.getResource()) {
            return f.apply(jedis);
        }
    }
}
