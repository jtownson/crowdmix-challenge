# Jparsec demo

## Contents
* Running the application
* App description
* Interesting libraries used

## Running the application

The application depends on JDK 8 and Maven 3.

    $ git clone https://github.com/jtownson/jparsec-demo.git
    $ cd jparsec-demo
    $ mvn clean package
    $ mvn exec:java -Dexec.mainClass="net.jtownson.jparsec.ConsoleApplication" 

This should drop you into a prompt where you can enter commands. CTRL-C will exit.

## App description
 
Implements a console-based social networking application (similar to Twitter) satisfying the scenarios below.
 Scenarios
 Posting: Alice can publish messages to a personal timeline
 
 > Alice -> I love the weather today
 > Bob -> Damn! We lost!
 > Bob -> Good game though.
 
 Reading: Bob can view Alice’s timeline
 
 > Alice
 I love the weather today (5 minutes ago)
 > Bob
 Good game though. (1 minute ago)
 Damn! We lost! (2 minutes ago)
 
 Following: Charlie can subscribe to Alice’s and Bob’s timelines, and view an aggregated list of all subscriptions
 
 > Charlie -> I'm in New York today! Anyone wants to have a coffee?
 > Charlie follows Alice
 > Charlie wall
 Charlie - I'm in New York today! Anyone wants to have a coffee? (2 seconds ago)
 Alice - I love the weather today (5 minutes ago)
 
 > Charlie follows Bob
 > Charlie wall
 Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)
 Bob - Good game though. (1 minute ago)
 Bob - Damn! We lost! (2 minutes ago)
 Alice - I love the weather today (5 minutes ago)
 Details
 
 Uses the console for input and output
 Users submit commands to the application. There are four commands. “posting”, “reading”, etc. are not part of the commands; commands always start with the user’s name.
         posting: <user name> -> <message>
         reading: <user name>
         following: <user name> follows <another user>
         wall: <user name> wall
 Currently no handling of invalid commands. One assume the user will always type the correct commands. 

## Interesting libraries used

jparsec (a port of Haskell's parsec parser combinator library)
totallylazy (a java fp library)
mock jedis (a redis stub)
