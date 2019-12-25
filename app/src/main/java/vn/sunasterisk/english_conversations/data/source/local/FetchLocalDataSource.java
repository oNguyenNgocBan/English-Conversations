package vn.sunasterisk.english_conversations.data.source.local;

import android.content.Context;

import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;
import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;
import vn.sunasterisk.english_conversations.data.source.ScoreDataSource;

public class FetchLocalDataSource implements CategoryDataSource.FetchCategoryDataSource,
        ScoreDataSource.GetScoreDataSource, ScoreDataSource.SaveScoreDataSource {

    private static FetchLocalDataSource sInstance;
    private Context mContext;

    private FetchLocalDataSource(Context context) {
        mContext = context;
    }

    public static void init(Context context) {
        sInstance = new FetchLocalDataSource(context);
    }

    public static FetchLocalDataSource getInstance() {
        return sInstance;
    }

    @Override
    public void getCategories(CategoryDataSource.OnFetchCategoryListener listener) {
        FetchCategoryFromLocal fetchCategoryFromLocal =
                new FetchCategoryFromLocal(listener);
        fetchCategoryFromLocal.fetchCategories(mContext);
    }

    @Override
    public int getTotalScoreOfConversation(Conversation conversation) {
        return new FetchScoreFromLocal(mContext).getTotalScoresOfConversation(conversation);
    }

    @Override
    public int getScoreOfSentence(Conversation conversation, int sentenceIndex) {
        return new FetchScoreFromLocal(mContext).getScoresOfSentences(conversation, sentenceIndex);
    }

    @Override
    public void saveScoreOfSentence(Conversation conversation, int sentenceIndex, int score) {
       new FetchScoreFromLocal(mContext).saveScore(conversation, sentenceIndex, score);
    }
}
