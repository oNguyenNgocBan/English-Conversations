package vn.sunasterisk.english_conversations.data.model;

public class Sentence {

    private String mText;
    private String mAudio;

    public Sentence() {
    }

    public Sentence(String text, String audio) {
        mText = text;
        mAudio = audio;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getAudio() {
        return mAudio;
    }

    public void setAudio(String audio) {
        mAudio = audio;
    }
}
