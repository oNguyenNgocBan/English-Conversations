package vn.sunasterisk.english_conversations.data.model;

import java.util.List;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.utils.StringUtils;

public class Category {

    private String mId;
    private String mTitle;
    private String mCover;
    private List<Conversation> mConversations;

    public Category() {
    }

    public Category(String id, String title, String cover, List<Conversation> conversations) {
        mId = id;
        mTitle = title;
        mCover = cover;
        mConversations = conversations;
    }

    public String getCoverFullUrl() {
        return StringUtils.formatFromBaseURL(Constant.CATEGORY_PATH, mId, mCover);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }

    public List<Conversation> getConversations() {
        return mConversations;
    }

    public void setConversations(List<Conversation> conversations) {
        mConversations = conversations;
    }
}
