package net.jtownson.jparsec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import static net.jtownson.jparsec.Context.inApplicationContext;

public class ConsoleApplication {

    public static void main(String[] args) {

        inApplicationContext(

                wiring -> {

                    Pipeline pipeline = new Pipeline(wiring.getParserFactory(), wiring.getMessagingFacade());

                    pipeline.process(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out, true));
                }
        );
    }
}
