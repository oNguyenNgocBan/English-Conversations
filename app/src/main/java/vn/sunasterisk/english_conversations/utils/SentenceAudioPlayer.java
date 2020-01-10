package vn.sunasterisk.english_conversations.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;

public class SentenceAudioPlayer {

    private MediaPlayer mediaPlayer;
    private Sentence mSentence;
    private Conversation mConversation;
    private Context mContext;

    public SentenceAudioPlayer(Context context,
                               Sentence sentence,
                               Conversation conversation) {
        mContext = context;
        mSentence = sentence;
        mConversation = conversation;
    }

    public void play() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return;
        }
        String root = Environment.getExternalStorageDirectory().toString();
        String savedLocation = mSentence.getAudioFullUrl(mConversation.getId()).replace(
                Constant.BASE_URL,
                Constant.EMPTY_STRING);
        String url = root + savedLocation;
        playAudio(url);
    }

    private void playAudio(String url) {
        Log.d("---", "playAudio: url " + url);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            try {
//                mediaPlayer.setDataSource(url);
//            } catch (IOException e) {
//                Log.d("---", "playAudio: " + e.getMessage());
//                e.printStackTrace();
//            }
            mediaPlayer = MediaPlayer.create(mContext, Uri.parse(url));
//            mediaPlayer = MediaPlayer.create(mContext, Uri.parse(mSentence.getAudioFullUrl(mConversation.getId())));
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                Log.d("---", "playAudio: " + e.getMessage());
                e.printStackTrace();
            }
        }
        mediaPlayer.start();
    }
}
