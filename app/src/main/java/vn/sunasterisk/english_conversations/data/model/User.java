package vn.sunasterisk.english_conversations.data.model;

import java.io.Serializable;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.utils.StringUtils;

public class User implements Serializable {

    private String mName;
    private String mAvatar;

    public User() {
    }

    public User(String name, String avatar) {
        mName = name;
        mAvatar = avatar;
    }

    public String getFullAvatarUrl(String conversationId) {
        return StringUtils.formatFromBaseURL(Constant.CONVERSATION_PATH, conversationId, mAvatar);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }
}
