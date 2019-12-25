package vn.sunasterisk.english_conversations.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.StringTokenizer;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.model.Conversation;

public class FetchScoreFromLocal {

    private static final String SHARED_PREFERENCES_NAME = "shared_preferences_scores";

    private SharedPreferences mPrefs;
    private Context mContext;

    public FetchScoreFromLocal(Context context) {
        mContext = context;
        mPrefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveScore(Conversation conversation, int sentenceIndex, int score) {
        ArrayList<Integer> currentScores = listScoresOfConversation(conversation);
        if (currentScores == null) {
            currentScores = new ArrayList<>();
            // create list score 0
            for (int i = 0; i < conversation.getSentences().size(); i++) {
                currentScores.add(0);
            }
        }
        currentScores.set(sentenceIndex, score);

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < currentScores.size(); i++) {
            str.append(currentScores.get(i).toString() + Constant.COMMA);
        }
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString(conversation.getId(), str.toString());
        edit.apply();
        edit.commit();
    }

    private ArrayList<Integer> listScoresOfConversation(Conversation conversation) {
        String savedString = mPrefs.getString(conversation.getId(), Constant.EMPTY_STRING);
        if (savedString.equals(Constant.EMPTY_STRING)) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(savedString, Constant.COMMA);
        ArrayList<Integer> savedScores = new ArrayList<>();
        for (int i = 0; i < st.countTokens(); i++) {
            savedScores.add(Integer.parseInt(st.nextToken()));
        }
        return savedScores;
    }

    public int getTotalScoresOfConversation(Conversation conversation) {
        ArrayList<Integer> scores = listScoresOfConversation(conversation);
        if (scores == null) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < scores.size(); i++) {
            total = total + scores.get(i);
        }
        return total;
    }

    public int getScoresOfSentences(Conversation conversation, int sentenceIndex) {
        ArrayList<Integer> scores = listScoresOfConversation(conversation);
        if (scores == null) {
            return 0;
        }
        return scores.get(sentenceIndex) == null ? 0 : scores.get(sentenceIndex);
    }
}
