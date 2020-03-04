package vn.sunasterisk.english_conversations.constant;

import vn.sunasterisk.english_conversations.utils.StringUtils;

public class Constant {
    public static final String REQUEST_METHOD_GET = "GET";
    public static final int CONNECT_TIME_OUT = 5000;
    public static final int READ_TIME_OUT = 5000;
    public static final int TIMER_DELAY = 100;
    public static final int MAX_SCORE = 100;
    public static final int VERY_GOOD_SCORE = 90;
    public static final int GOOD_SCORE = 60;
    public static final int ONE_HUNDRED = 100;
    public static final String BREAK_LINE = "\n";
    public static final String BASE_URL = "https://english-conversation-160713.web.app";
    public static final String DATA_JSON_URL = StringUtils.formatFromBaseURL("data.json");
    public static final String CATEGORY_PATH = "category";
    public static final String CONVERSATION_PATH = "converstation";
    public static final String DATA_JSON_NAME = "data.json";
    public static final String SLASH = "/";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String TWO_DOT = ":";
    public static final String EMPTY_STRING = "";
}
