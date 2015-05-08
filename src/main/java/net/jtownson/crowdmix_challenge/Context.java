package net.jtownson.crowdmix_challenge;

import net.jtownson.crowdmix_challenge.messaging.MessagingFacade;
import net.jtownson.crowdmix_challenge.messaging.patch.MockJedisPool;
import net.jtownson.crowdmix_challenge.messaging.services.MessagingSchema;
import net.jtownson.crowdmix_challenge.messaging.services.MessagingService;
import net.jtownson.crowdmix_challenge.messaging.services.RedisMessagingSchema;
import net.jtownson.crowdmix_challenge.output.ElapsedTime;
import net.jtownson.crowdmix_challenge.output.OutputFormatting;
import net.jtownson.crowdmix_challenge.parsing.ParserFactory;
import redis.clients.jedis.JedisPool;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Context {

    private MessagingFacade messagingFacade;
    private MessagingService messagingService;
    private MessagingSchema messagingSchema;
    private OutputFormatting outputFormatting;
    private ParserFactory parserFactory;
    private JedisPool jedisPool;

    public Context() {
        messagingFacade = getMessagingFacade();
        messagingService = getMessagingService();
        outputFormatting = getOutputFormatting();
        parserFactory = getParserFactory();
        jedisPool = getJedisPool();
    }

    public static void inApplicationContext(Consumer<Context> someCode) {
        someCode.accept(new Context());
    }

    public MessagingFacade getMessagingFacade() {
        return lazily(messagingFacade, () -> new MessagingFacade(getMessagingService(), getOutputFormatting()), cp -> messagingFacade = cp);
    }

    public MessagingService getMessagingService() {
        return lazily(messagingService, () -> new MessagingService(getMessagingSchema()), ms -> messagingService = ms);
    }

    public MessagingSchema getMessagingSchema() {
        return lazily(messagingSchema, () -> new RedisMessagingSchema(getJedisPool()), ms -> messagingSchema = ms);
    }

    public OutputFormatting getOutputFormatting() {
        return lazily(outputFormatting, () -> new OutputFormatting(new ElapsedTime(System::currentTimeMillis)), of -> outputFormatting = of);
    }

    public ParserFactory getParserFactory() {
        return lazily(parserFactory, ParserFactory::new, pf -> parserFactory = pf);
    }

    private JedisPool getJedisPool() {
        return lazily(jedisPool, MockJedisPool::new, jp -> jedisPool = jp);
    }

    private static <T> T lazily(T currentVal, Supplier<T> getter, Consumer<T> setter) {
        if (currentVal == null) {
            currentVal = getter.get();
            setter.accept(currentVal);
        }
        return currentVal;
    }
}
