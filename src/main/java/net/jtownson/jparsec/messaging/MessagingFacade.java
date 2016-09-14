package net.jtownson.jparsec.messaging;

import net.jtownson.jparsec.messaging.services.Message;
import net.jtownson.jparsec.messaging.services.MessagingService;
import net.jtownson.jparsec.output.OutputFormatting;
import net.jtownson.jparsec.parsing.Commands.*;

import java.util.List;

public class MessagingFacade {

    private MessagingService messagingService;
    private OutputFormatting outputFormatting;

    public MessagingFacade(MessagingService messagingService, OutputFormatting outputFormatting) {
        this.messagingService = messagingService;
        this.outputFormatting = outputFormatting;
    }

    public String handle(Command command) {

        if (command instanceof WallCommand) {

            return wall((WallCommand) command);

        } else if (command instanceof ReadCommand) {

            return read((ReadCommand) command);

        } else if (command instanceof FollowsCommand) {

            return follows((FollowsCommand) command);

        } else if (command instanceof PostCommand) {

            return post((PostCommand) command);
        } else if (command instanceof SkipCommand) {

            return outputFormatting.blankResponse();
        }
        return outputFormatting.confused();
    }

    private String post(PostCommand command) {
        Message message = postCommand2Message(command);
        messagingService.postMessage(message);
        return outputFormatting.posted(message);
    }

    private Message postCommand2Message(PostCommand postCommand) {
        return new Message(postCommand.getUserName(), postCommand.getBody(), System.currentTimeMillis());
    }

    private String follows(FollowsCommand command) {
        String subject = command.getSubjectUserName();
        String object = command.getObjectUserName();
        messagingService.follow(subject, object);
        return outputFormatting.following(subject, object);
    }

    private String read(ReadCommand command) {
        List<Message> messages = messagingService.readTimeline(command.getUserName());
        return outputFormatting.timelineMessages(messages);
    }

    private String wall(WallCommand command) {
        List<Message> messages = messagingService.readWall(command.getUserName());
        return outputFormatting.wallMessages(messages);
    }
}
