package vn.sunasterisk.english_conversations.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import vn.sunasterisk.english_conversations.constant.Constant;

public class StringUtils {

    public static String formatFromBaseURL(String path) {
        return String.format("%s/%s", Constant.BASE_URL, path);
    }

    public static String formatFromBaseURL(String path1, String path2) {
        return String.format("%s/%s/%s", Constant.BASE_URL, path1, path2);
    }

    public static String formatFromBaseURL(String path1, String path2, String path3) {
        return String.format("%s/%s/%s/%s", Constant.BASE_URL, path1, path2, path3);
    }

    public static String formatProgress(int currentStarCount, int totalStarCount) {
        return String.format("%d/%d", currentStarCount, totalStarCount);
    }

    public static String getNameFromFileURL(String url) {
        return url.substring(url.lastIndexOf(Constant.SLASH) + 1, url.lastIndexOf(Constant.DOT));
    }

    public static String removePunctuation(String input) {
        return input.replaceAll("[^a-zA-Z ]", Constant.EMPTY_STRING);
    }

    public static String[] splitBySpace(String input) {
        return input.split("\\s+");
    }
}
