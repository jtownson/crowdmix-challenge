package net.jtownson.crowdmix_challenge.messaging.services;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Message {

    private final String userName;
    private final String body;
    private final Long timestamp;

    public Message(String userName, String body, Long timestamp) {
        this.userName = userName;
        this.body = body;
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public String getBody() {
        return body;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        return new EqualsBuilder()
                .append(userName, message1.userName)
                .append(body, message1.body)
                .append(timestamp, message1.timestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userName)
                .append(body)
                .append(timestamp)
                .toHashCode();
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", userName, body, format(timestamp));
    }

    private static String format(Long timestamp) {

        long now = System.currentTimeMillis();
        long elapsedSecs = (now - timestamp)/1000;
        long elapsedMins = (long)((double)elapsedSecs / 60.0);

        if (elapsedSecs <= 30) {
            return "moments ago";
        } else {
            return elapsedMins + " ago";
        }
    }
}
