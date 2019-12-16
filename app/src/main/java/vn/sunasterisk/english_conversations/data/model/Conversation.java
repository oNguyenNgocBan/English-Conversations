package vn.sunasterisk.english_conversations.data.model;

import java.util.List;

import vn.sunasterisk.english_conversations.constant.Constant;

public class Conversation {

    private String mId;
    private String mTitle;
    private String mLogoImage;
    private String mAudio;

    private User mUserA;
    private User mUserB;

    private List<Sentence> mSentences;

    public Conversation() {
    }

    private String getCategoryFullUrl() {
        return Constant.CONVERSATION_URL + mId.toString() + Constant.SLASH;
    }

    public String getLogoFullUrl() {
        return getCategoryFullUrl() + mLogoImage;
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

    public String getLogoImage() {
        return mLogoImage;
    }

    public void setLogoImage(String logoImage) {
        mLogoImage = logoImage;
    }

    public String getAudio() {
        return mAudio;
    }

    public void setAudio(String audio) {
        mAudio = audio;
    }

    public User getUserA() {
        return mUserA;
    }

    public void setUserA(User userA) {
        mUserA = userA;
    }

    public User getUserB() {
        return mUserB;
    }

    public void setUserB(User userB) {
        mUserB = userB;
    }

    public List<Sentence> getSentences() {
        return mSentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.mSentences = sentences;
    }
}
