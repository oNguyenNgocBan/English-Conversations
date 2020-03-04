package vn.sunasterisk.english_conversations.utils;

import vn.sunasterisk.english_conversations.constant.Constant;

public class TimeUtils {

    private static final int MILISECONDS_IN_HOUR = 1000 * 60 * 60;
    private static final int MINISECONDS_IN_MINUTES = 1000 * 60;
    private static final int MINISECONDS_IN_SECONDS = 1000;

    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        int hours = (int) (milliseconds / MILISECONDS_IN_HOUR);
        int minutes = (int) (milliseconds % MILISECONDS_IN_HOUR) / MINISECONDS_IN_MINUTES;
        int seconds = (int) ((milliseconds % MILISECONDS_IN_HOUR) % MINISECONDS_IN_MINUTES
                / MINISECONDS_IN_SECONDS);
        if (hours > 0) {
            finalTimerString = hours + Constant.TWO_DOT;
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = Constant.EMPTY_STRING + seconds;
        }

        finalTimerString = finalTimerString + minutes + Constant.TWO_DOT + secondsString;

        return finalTimerString;
    }

    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;
        percentage = (((double) currentDuration) / totalDuration) * Constant.ONE_HUNDRED;
        return percentage.intValue();
    }

    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / MINISECONDS_IN_SECONDS);
        currentDuration = (int) ((((double) progress) / Constant.ONE_HUNDRED) * totalDuration);

        return currentDuration * MINISECONDS_IN_SECONDS;
    }
}
