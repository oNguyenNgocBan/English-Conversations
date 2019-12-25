package vn.sunasterisk.english_conversations.data.repository;

import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.source.local.FetchLocalDataSource;

public class ScoreRepository {

    private static ScoreRepository sInstance;
    private FetchLocalDataSource mFetchLocalDataSource;

    private ScoreRepository(FetchLocalDataSource fetchLocalDataSource) {
        mFetchLocalDataSource = fetchLocalDataSource;
    }

    public static ScoreRepository getInstance() {
        if (sInstance == null) {
            sInstance = new ScoreRepository(FetchLocalDataSource.getInstance());
        }
        return sInstance;
    }

    public int getTotalScoresOfConversation(Conversation conversation) {
        return mFetchLocalDataSource.getTotalScoreOfConversation(conversation);
    }

    public int getScoresOfSentences(Conversation conversation, int sentenceIndex) {
        return mFetchLocalDataSource.getScoreOfSentence(conversation, sentenceIndex);
    }

    public void saveScoreOfSentence(Conversation conversation, int sentenceIndex, int score) {
        mFetchLocalDataSource.saveScoreOfSentence(conversation, sentenceIndex, score);
    }
}
