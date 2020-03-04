package vn.sunasterisk.english_conversations.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        List<Integer> currentScores = getScoresOfConversation(conversation);
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
            if (i == 0) {
                str.append(currentScores.get(i).toString());
            } else {
                str.append(Constant.COMMA + currentScores.get(i).toString());
            }
        }
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString(conversation.getId(), str.toString());
        edit.apply();
    }

    private List<Integer> getScoresOfConversation(Conversation conversation) {
        String savedString = mPrefs.getString(conversation.getId(), Constant.EMPTY_STRING);
        if (savedString.equals(Constant.EMPTY_STRING)) {
            return null;
        }
        List<String> scoresString = Arrays.asList(savedString.split(Constant.COMMA));
        List<Integer> savedScores = new ArrayList<>();
        for (String score : scoresString) {
            savedScores.add(Integer.parseInt(score));
        }
        return savedScores;
    }

    public int getTotalScoresOfConversation(Conversation conversation) {
        List<Integer> scores = getScoresOfConversation(conversation);
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
        List<Integer> scores = getScoresOfConversation(conversation);
        if (scores == null) {
            return 0;
        }
        if (sentenceIndex >= scores.size()) {
            return 0;
        }
        return scores.get(sentenceIndex) == null ? 0 : scores.get(sentenceIndex);
    }
}
