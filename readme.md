# Jeremy Townson's Crowdmix challenge - notes

## Contents
* Running the application
* Parsing user commands
* Message handling
* Application flow
* Dependency injection
* Testing

## Running the application

The application depends on JDK 8 and Maven 3.

    $ git clone https://github.com/jtownson/crowdmix-challenge.git
    $ cd crowdmix-challenge
    $ mvn clean package
    $ mvn exec:java -Dexec.mainClass="net.jtownson.crowdmix_challenge.ConsoleApplication" 

This should drop you into a prompt where you can enter commands. CTRL-C will exit.

## Parsing user commands

I have used a great library, called [JParsec] (https://github.com/jparsec/jparsec) which is simple enough to 
make command processing easy and general enough to accommodate additional commands and edge cases. 
 
## Message handling

The outstanding feature/requirement of the example scenarios is that, when Charlie follows Bob, Charlie's wall includes Bob's posts 
from *before* the time he/she began to follow Bob. In other words, Charlie is not subscribing to Bob's posts from after the
time he began to follow Bob but before *and* after. Following is retroactive so this is not a traditional pub/sub use-case.

Another notable point from the example scenarios is that users are created lazily upon posting a message.

A non-functional requirement I added myself is that it would be mad to support the messaging use-cases in a way that did not
scale or support persistence because, even if this is a demo, any real messaging system has large numbers of persisted messages.

For these reasons I chose to use (a mockup of) the [Redis] (http://redis.io/) key/value store. This choice has the following features

1. Scalability and persistence for free by wiring a real redis client (pointing to a real Redis server) in place of the redis test stub.
2. Simpler service class implementation. The app messaging service has no state of its own to manage but just barks commands at Redis.
3. There is a [mock Redis] (https://github.com/50onRed/mock-jedis) on github to support demo and test scenarios.
4. Redis adds data lazily, in line with the requirement to create users lazily.

## Application flow

I used functional programming techniques to model the application as
a pipeline of user input --> parsed command objects --> processing response --> user output. 

The functional java library, [totallylazy] (http://http://totallylazy.com/) makes the implementation neat.
 
Would I have done this in production code instead of a conventional while loop? Probably not. This is a software craftmanship
challenge and the pipeline code is very pretty.

## Dependency injection

Wiring of application dependencies is handled by net.jtownson.crowdmix_challenge.Context.

## Testing

There is unit test coverage for

1. user input parsing (net.jtownson.crowdmix_challenge.parsing.ParserFactoryTest)
2. message handling (net.jtownson.crowdmix_challenge.messaging.services.MessagingServiceTest)
3. user output (net.jtownson.crowdmix_challenge.output.OutputFormattingTest)

Finally, there is an acceptance test to cover the scenarios in the challenge description 
(net.jtownson.crowdmix_challenge.acceptance.AcceptanceTest)