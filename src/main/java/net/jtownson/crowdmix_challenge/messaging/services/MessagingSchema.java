package net.jtownson.crowdmix_challenge.messaging.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MessagingSchema {

    void hmset(String key, Map<String, String> properties);

    List<String> hmget(String key, String... fields);

    long lpush(String key, String value);

    List<String> lrange(String key, long start, long end);

    Set<String> smembers(String key);

    long sadd(String key, String value);

    long incr(String key);
}
