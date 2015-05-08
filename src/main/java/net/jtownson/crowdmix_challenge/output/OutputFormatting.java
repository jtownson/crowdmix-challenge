package net.jtownson.crowdmix_challenge.output;

import net.jtownson.crowdmix_challenge.messaging.services.Message;

import java.util.List;

public class OutputFormatting {

    private final ElapsedTime elapsedTime;

    public OutputFormatting(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String timelineMessages(List<Message> messages) {
        return messages(messages, false);
    }

    public String wallMessages(List<Message> messages) {
        return messages(messages, true);
    }

    public String following(String subject, String object) {
        return "";
    }

    public String posted(Message message) {
        return "";
    }

    public String confused() {
        return "I didn'ae understand ye!";
    }

    public String blankResponse() {
        return "";
    }

    private String messages(List<Message> messages, boolean withUsernamePrefix) {
        StringBuilder buffer = new StringBuilder();
        messages.forEach(message -> message(message, buffer, withUsernamePrefix));
        return buffer.toString();
    }

    private void message(Message message, StringBuilder buffer, boolean withUsername) {
        buffer.append(format(message, withUsername)).append('\n');
    }

    private String format(Message message, boolean withUsername) {
        return withUsername ?
                String.format("%s - %s (%s)", message.getUserName(), message.getBody(), format(message.getTimestamp())) :
                String.format("%s (%s)", message.getBody(), format(message.getTimestamp()));
    }

    private String format(Long timestamp) {

        HoursMinsSecs hms = elapsedTime.getHMS(timestamp);

        if (hms.hours() > 0) {
            return String.format("%s %s ago", hms.hours(), grammar(hms.hours(), "hour", "hours"));
        } else if (hms.minutes() > 0) {
            return String.format("%s %s ago", hms.minutes(), grammar(hms.minutes(), "minute", "minutes"));
        } else {
            return "seconds ago";
        }
    }

    private String grammar(long t, String singular, String plural) {
        return t == 1 ? singular : plural;
    }

}
