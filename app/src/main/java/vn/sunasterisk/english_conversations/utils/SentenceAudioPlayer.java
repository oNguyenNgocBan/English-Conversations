package vn.sunasterisk.english_conversations.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;

public class SentenceAudioPlayer {

    private MediaPlayer mediaPlayer;
    private Sentence mSentence;
    private Conversation mConversation;
    private SentenceAudioPlayerListener mSentenceAudioPlayerListener;

    public SentenceAudioPlayer(Sentence sentence, Conversation conversation,
                               SentenceAudioPlayerListener sentenceAudioPlayerListener) {
        mSentence = sentence;
        mConversation = conversation;
        mSentenceAudioPlayerListener = sentenceAudioPlayerListener;
    }

    public void play() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return;
        }
        String audioUrl;

        String root = Environment.getExternalStorageDirectory().toString();
        String savedLocation = mSentence.getAudioFullUrl(mConversation.getId()).replace(
                Constant.BASE_URL,
                Constant.EMPTY_STRING);
        String savedAudioUrl = root + savedLocation;
        File savedFile = new File(savedAudioUrl);

        if (savedFile.exists()) {
            audioUrl = savedAudioUrl;
        } else {
            audioUrl = mSentence.getAudioFullUrl(mConversation.getId());
        }

        playAudio(audioUrl);
    }

    private void playAudio(String url) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(url);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mSentenceAudioPlayerListener.onSentencePlayCompleted();
                    }
                });
                mediaPlayer.prepare();
            } catch (Exception e) {
                mSentenceAudioPlayerListener.onSentencePlayFailure(e.getMessage());
            }
        }
        mediaPlayer.start();
    }

    public interface SentenceAudioPlayerListener {
        void onSentencePlayCompleted();

        void onSentencePlayFailure(String message);
    }
}
