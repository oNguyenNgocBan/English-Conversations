package vn.sunasterisk.english_conversations.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;

public class AudioDownloader extends AsyncTask<Conversation, Integer, Void> {

    private Context mContext;
    private AudioDownloaderListener mAudioDownloaderListener;
    private Exception mException;

    public AudioDownloader(AudioDownloaderListener audioDownloaderListener) {
        this.mAudioDownloaderListener = audioDownloaderListener;
        if (audioDownloaderListener instanceof Context) {
            mContext = (Context) audioDownloaderListener;
        }
    }

    @Override
    protected Void doInBackground(Conversation... conversations) {
        for (Conversation conversation : conversations) {
            String conversationAudioUrlString = conversation.getAudioFullUrl();
            downloadAudio(conversationAudioUrlString);
            List<Sentence> sentences = conversation.getSentences();
            for (Sentence sentence : sentences) {
                String sentenceAudioUrlString = sentence.getAudioFullUrl(conversation.getId());
                downloadAudio(sentenceAudioUrlString);
            }
        }
        return null;
    }

    private void downloadAudio(String urlString) {
        String root = Environment.getExternalStorageDirectory().toString();
        String savedLocation = urlString.replace(
                Constant.BASE_URL,
                Constant.EMPTY_STRING);
        String fileOutput = root + savedLocation;
        File file = new File(fileOutput);

        Log.d("---", "downloadAudio: ----------------------");
//        Log.d("---", "downloadAudio: urlString " + urlString);
//        Log.d("---", "downloadAudio: savedLocation " + savedLocation);
        Log.d("---", "downloadAudio: root " + root);
        Log.d("---", "downloadAudio: fileOutput " + fileOutput);

        if (file.exists()) {
            return;
        }

        InputStream input = null;
        HttpURLConnection connection = null;
        OutputStream output = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                mException = new Exception(mContext.getString(R.string.message_invalid_url));
                return;
            }

            input = connection.getInputStream();
            output = new FileOutputStream(fileOutput);

            byte datas[] = new byte[4096];
            int count;
            while ((count = input.read(datas)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return;
                }
                output.write(datas, 0, count);
            }
        } catch (Exception e) {
            Log.d("---", "downloadAudio: ");
            mException = e;
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException e) {
                Log.d("---", "downloadAudio: ");
                mException = e;
            }
            if (connection != null)
                connection.disconnect();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mAudioDownloaderListener != null) {
            if (mException != null) {
                mAudioDownloaderListener.onDownloadFailure(mException.getMessage());
            } else {
                mAudioDownloaderListener.onDownloadSuccess();
            }
        }
    }

    public interface AudioDownloaderListener {
        void onDownloadSuccess();

        void onDownloadFailure(String message);
    }
}