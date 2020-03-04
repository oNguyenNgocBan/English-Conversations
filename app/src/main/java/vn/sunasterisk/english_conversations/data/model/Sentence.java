package vn.sunasterisk.english_conversations.data.model;

import java.io.Serializable;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.repository.ScoreRepository;
import vn.sunasterisk.english_conversations.utils.StringUtils;

public class Sentence implements Serializable {

    private String mText;
    private String mAudio;

    public Sentence() {
    }

    public Sentence(String text, String audio) {
        mText = text;
        mAudio = audio;
    }

    public String getAudioFullUrl(String conversationId) {
        return StringUtils.formatFromBaseURL(Constant.CONVERSATION_PATH, conversationId, mAudio);
    }

    public int getScrore(Conversation conversation) {
        ScoreRepository scoreRepository = ScoreRepository.getInstance();
        int sentenceIndex = conversation.getSentences().indexOf(this);
        return scoreRepository.getScoresOfSentences(conversation, sentenceIndex);
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
