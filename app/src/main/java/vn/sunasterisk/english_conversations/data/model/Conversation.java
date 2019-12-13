package vn.sunasterisk.english_conversations.data.model;

import java.util.List;

public class Conversation {

    private String mId;
    private String mTitle;
    private String mImageLogo;
    private String mAudio;

    private User mUserA;
    private User mUserB;

    private List<Sentence> nSentences;

    public Conversation() {
    }

    public Conversation(String id,
                        String title,
                        String imageLogo,
                        String audio,
                        User userA,
                        User userB,
                        List<Sentence> sentences) {
        mId = id;
        mTitle = title;
        mImageLogo = imageLogo;
        mAudio = audio;
        mUserA = userA;
        mUserB = userB;
        this.nSentences = sentences;
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

    public String getImageLogo() {
        return mImageLogo;
    }

    public void setImageLogo(String imageLogo) {
        mImageLogo = imageLogo;
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
        return nSentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.nSentences = sentences;
    }
}
