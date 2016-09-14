package net.jtownson.jparsec.output;

import java.util.function.LongSupplier;

public class ElapsedTime {

    private final LongSupplier timestampSupplier;

    public ElapsedTime(LongSupplier timestampSupplier) {
        this.timestampSupplier = timestampSupplier;
    }


    public HoursMinsSecs getHMS(long timestamp) {

        long now = timestampSupplier.getAsLong();

        long elapsedSecs = (now - timestamp)/1000;

        long hours = elapsedSecs / 3600;
        long minutes = (elapsedSecs % 3600) / 60;
        long seconds = elapsedSecs % 60;

        return new HoursMinsSecs(hours, minutes, seconds);
    }
}
