package net.jtownson.jparsec.messaging.patch;

import com.fiftyonred.mock_jedis.MockJedis;

import java.util.List;

import static com.googlecode.totallylazy.collections.PersistentList.constructors.list;

public class PatchedMockJedis extends MockJedis {

    public PatchedMockJedis(String host) {
        super(host);
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return list(super.lrange(key, start, end)).reverse();
    }
}
