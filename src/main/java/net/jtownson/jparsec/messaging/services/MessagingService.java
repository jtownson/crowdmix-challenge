package net.jtownson.jparsec.messaging.services;

import com.googlecode.totallylazy.Sequence;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.collections.PersistentList.constructors.list;
import static com.googlecode.totallylazy.collections.PersistentMap.constructors.map;
import static java.util.Comparator.comparing;

/**
 * Does the posting, reading of messages and following.
 */
public class MessagingService {

    public static final String timeline = "timeline";
    public static final String postCounter = "postCounter";
    public static final String post = "post";
    public static final String following = "following";

    private final MessagingSchema messagingSchema;

    public MessagingService(MessagingSchema messagingSchema) {
        this.messagingSchema = messagingSchema;
    }

    public void postMessage(Message message) {

        String userName = message.getUserName();

        String postId = nextPostId();

        savePost(message, postId);

        addPostIdToUserTimeline(postId, userName);

        follow(userName, userName);
    }

    public List<Message> readTimeline(String userName) {

        List<String> postNums = lrange(key(timeline, userName), 0, -1);

        return toMessages(postNums);
    }

    public void follow(String subject, String object) {

        sadd(key(subject, following), object);
    }

    public List<Message> readWall(String userName) {

        Sequence<Message> messages = sequence(smembers(key(userName, following))).flatMap(this::readTimeline);

        return list(messages.sortBy(comparing(Message::getTimestamp).reversed()));
    }


    private String nextPostId() {
        return String.valueOf(messagingSchema.incr(postCounter));
    }

    private void savePost(Message message, String postId) {

        messagingSchema.hmset(

                key(post, postId),

                map("userName", message.getUserName(),
                        "message", message.getBody(),
                        "timestamp", String.valueOf(message.getTimestamp())));
    }

    private void addPostIdToUserTimeline(String postId, String userName) {
        messagingSchema.lpush(key(timeline, userName), postId);
    }


    private List<String> hmget(String key, String... fields) {
        return messagingSchema.hmget(key, fields);
    }

    private List<String> lrange(String key, long start, long end) {
        return messagingSchema.lrange(key, start, end);
    }

    private Set<String> smembers(String key) {
        return messagingSchema.smembers(key);
    }

    private long sadd(String key, String value) {
        return messagingSchema.sadd(key, value);
    }

    private List<Message> toMessages(List<String> postNums) {
        return list(postNums).
                map(postNum -> toMessage(hmget(key("post", postNum), "userName", "message", "timestamp")));
    }

    private Message toMessage(List<String> hmgetResult) {
        return new Message(hmgetResult.get(0), hmgetResult.get(1), Long.valueOf(hmgetResult.get(2)));
    }

    private String key(Object ... components) {
        return StringUtils.join(components, ':');
    }
}
