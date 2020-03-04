package vn.sunasterisk.english_conversations.utils;

import java.util.Arrays;
import java.util.List;

import vn.sunasterisk.english_conversations.constant.Constant;

public class MarkScoreUtils {

    public static final int getScore(String correctAnswer, String userSpeech) {
        String formattedTextUserSpeech = StringUtils.removePunctuation(userSpeech).toLowerCase();
        String formattedTextCorrect = StringUtils.removePunctuation(correctAnswer).toLowerCase();

        List<String> userSpeechWords =
                Arrays.asList(StringUtils.splitBySpace(formattedTextUserSpeech));
        List<String> textCorrectWords =
                Arrays.asList(StringUtils.splitBySpace(formattedTextCorrect));

        int score;
        if (formattedTextUserSpeech.equals(formattedTextCorrect)) {
            score = Constant.MAX_SCORE;
        } else {
            int matchCount = 0;
            int wrongCount = 0;
            for (String userSpeechWord : userSpeechWords) {
                if (textCorrectWords.contains(userSpeechWord)) {
                    matchCount += 1;
                } else {
                    wrongCount += 1;
                }
            }

            for (String textCorrectWord : textCorrectWords) {
                if (!userSpeechWords.contains(textCorrectWord)) {
                    wrongCount += 1;
                }
            }

            int wrongLimitPercent = 50;
            double wrongPercent = wrongCount * Constant.ONE_HUNDRED / textCorrectWords.size();
            if (wrongPercent > wrongLimitPercent) {
                score = 0;
            } else {
                double correctPercent = matchCount * Constant.ONE_HUNDRED / textCorrectWords.size();
                score = (int) (correctPercent);
            }
        }
        return score;
    }
}
