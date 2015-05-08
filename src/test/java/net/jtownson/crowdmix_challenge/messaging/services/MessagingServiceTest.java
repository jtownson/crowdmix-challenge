package net.jtownson.crowdmix_challenge.messaging.services;

import net.jtownson.crowdmix_challenge.messaging.patch.MockJedisPool;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.googlecode.totallylazy.collections.PersistentList.constructors.list;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MessagingServiceTest {

    private MessagingService messagingService;

    @Before
    public void setup() {

        MessagingSchema messagingSchema = new RedisMessagingSchema(new MockJedisPool());

        messagingService = new MessagingService(messagingSchema);
    }

    @Test
    public void givenAMessageIsPostedToATimelineItShouldBeReadable() {

        // given
        String timeline = "Alice";
        Message message = new Message(timeline, "I love the weather today", 1L);
        messagingService.postMessage(message);

        // when
        List<Message> messages = messagingService.readTimeline(timeline);

        // then
        assertThat(messages, is(list(message)));
    }

    @Test
    public void givenTwoMessagesArePostedTheyShouldBeReadNewestFirst() {

        // given
        String timeline = "Alice";
        Message firstMessage = new Message(timeline, "I love the weather today", 1L);
        Message secondMessage = new Message(timeline, "Now it's a bit cloudy", 1000L);

        messagingService.postMessage(firstMessage);
        messagingService.postMessage(secondMessage);

        // when
        List<Message> messages = messagingService.readTimeline(timeline);

        // then
        assertThat(messages, is(list(secondMessage, firstMessage)));
    }

    @Test
    public void givenAUserInNotFollowingAnybodyTheirWallAndTimelineShouldBeEqual() {

        // given
        String userName = "Alice";
        Message message = new Message(userName, "I love the weather today", 1L);
        messagingService.postMessage(message);

        // when
        List<Message> wall = messagingService.readWall(userName);
        List<Message> timeline = messagingService.readTimeline(userName);

        // then
        assertThat(timeline, is(list(message)));
        assertThat(wall, is(list(message)));
    }

    @Test
    public void givenAliceFollowsBobThenAlicesWallShouldIncludeBobsPosts_IncludingPreviousPosts() {

        // given
        String alice = "Alice";
        String bob = "Bob";

        Message bobMsg1 = new Message(bob, "Damn! We lost!", 1L);
        messagingService.postMessage(bobMsg1);

        // when
        messagingService.follow(alice, bob);
        Message bobMsg2 = new Message(bob, "Good game though", 2L);
        messagingService.postMessage(bobMsg2);

        List<Message> alicesWall = messagingService.readWall(alice);

        // then
        assertThat(alicesWall, is(list(bobMsg2, bobMsg1)));
    }
}