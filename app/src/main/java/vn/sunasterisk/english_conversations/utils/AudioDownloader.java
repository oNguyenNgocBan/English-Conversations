package vn.sunasterisk.english_conversations.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

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
        StorageManager storageManager = StorageManager.getInstance();
        for (Conversation conversation : conversations) {
            if (!storageManager.isExistFile(conversation.savedAudioFileName())) {
                String conversationAudioUrlString = conversation.getAudioFullUrl();
                downloadAudio(conversationAudioUrlString);
            }

            List<Sentence> sentences = conversation.getSentences();
            for (Sentence sentence : sentences) {
                if (!storageManager.isExistFile(sentence.savedAudioFileName(conversation.getId()))) {
                    String sentenceAudioUrlString = sentence.getAudioFullUrl(conversation.getId());
                    downloadAudio(sentenceAudioUrlString);
                }
            }
        }
        return null;
    }

    private void downloadAudio(String urlString) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;

        FileOutputStream outputStream = null;

        try {
            URL url = new URL(urlString);

            Log.d("---", "downloadAudio: urlString " + urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                mException = new Exception(mContext.getString(R.string.message_invalid_url));
                return;
            }

            input = connection.getInputStream();
            String savedLocation = urlString.replace(
                    Constant.BASE_URL + Constant.SLASH,
                    Constant.EMPTY_STRING);
            savedLocation = savedLocation.replace(Constant.SLASH, Constant.EMPTY_STRING);

            Log.d("---", "downloadAudio: savedLocation " + savedLocation);

//            output = new FileOutputStream(savedLocation);
            outputStream = mContext.openFileOutput(savedLocation, Context.MODE_PRIVATE);

            byte data[] = new byte[4096];
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return;
                }
//                output.write(data, 0, count);
                outputStream.write(data);
            }
        } catch (Exception e) {
            mException = e;
            Log.d("---:", "downloadAudio: " + e.getMessage());
            return;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
                mException = ignored;
                Log.d("---:", "downloadAudio: " + ignored.getMessage());
                return;
            }
            if (connection != null)
                connection.disconnect();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Log.d("---", "onPostExecute: ");

        if (mAudioDownloaderListener != null) {
            if (mException != null) {
                mAudioDownloaderListener.onDownloadFailure(mException.getMessage());
                Log.d("---", "onPostExecute: onDownloadFailure " + mException.getMessage());
            } else {
                mAudioDownloaderListener.onDownloadSuccess();
                Log.d("---", "onPostExecute: onDownloadSuccess");
            }
        }
    }

    public interface AudioDownloaderListener {
        void onDownloadSuccess();

        void onDownloadFailure(String message);
    }
}