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

    public int getStar(Conversation conversation) {
        int star;
        int score = getScrore(conversation);
        if (score == 0) {
            star = 0;
        } else if (score < 30) {
            star = 1;
        } else if (score < 45) {
            star = 2;
        } else if (score < 60) {
            star = 3;
        } else if (score < 90) {
            star = 4;
        } else {
            star = 5;
        }
        return star;
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
