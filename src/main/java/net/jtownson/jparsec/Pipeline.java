package net.jtownson.jparsec;

import com.googlecode.totallylazy.Callable1;
import net.jtownson.jparsec.messaging.MessagingFacade;
import net.jtownson.jparsec.parsing.ParserFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static com.googlecode.totallylazy.Sequences.repeat;

public class Pipeline {

    private final String prompt = "> ";
    private final ParserFactory parserFactory;
    private final MessagingFacade messagingFacade;

    public Pipeline(ParserFactory parserFactory, MessagingFacade messagingFacade) {
        this.parserFactory = parserFactory;
        this.messagingFacade = messagingFacade;
    }

    public void process(BufferedReader reader, PrintWriter printWriter) {

        try {

            printWriter.printf(prompt);

            repeat(readCommand(reader)).
               map(parserFactory::parse).
               map(messagingFacade::handle).
               map(writeResponse(printWriter)).
           forEach(userPrompt());

        } catch(Exception e) { /* catch CTRL-C or invalid command */ }
    }

    private Consumer<PrintWriter> userPrompt() {
        return printWriter -> printWriter.printf(prompt);
    }

    private Callable1<String, PrintWriter> writeResponse(PrintWriter printWriter) {
        return output -> { printWriter.printf(output); return printWriter; };
    }

    private Callable<String> readCommand(BufferedReader reader) {
        return reader::readLine;
    }
}
