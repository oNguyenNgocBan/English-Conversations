package vn.sunasterisk.english_conversations.data.model;

public class User {

    private String mName;
    private String mAvatar;

    public User() {
    }

    public User(String name, String avatar) {
        mName = name;
        mAvatar = avatar;
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
