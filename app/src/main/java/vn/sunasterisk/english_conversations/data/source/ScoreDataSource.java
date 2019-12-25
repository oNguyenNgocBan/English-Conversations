package vn.sunasterisk.english_conversations.data.source;

import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;

public interface ScoreDataSource {

    public interface GetScoreDataSource {
        int getTotalScoreOfConversation(Conversation conversation);

        int getScoreOfSentence(Conversation conversation, int sentenceIndex);
    }

    public interface SaveScoreDataSource {
        void saveScoreOfSentence(Conversation conversation, int sentenceIndex, int score);
    }
}
