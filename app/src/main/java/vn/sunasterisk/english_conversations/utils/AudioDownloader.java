package vn.sunasterisk.english_conversations.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
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
        String savedPath = urlString.replace(Constant.BASE_URL,Constant.EMPTY_STRING);
        String saveURL = Environment.getExternalStorageDirectory().toString() + savedPath;
        File file = new File(saveURL);

        if (file.exists()) {
            return;
        } else {
            String folderPath = saveURL.replace(StringUtils.getNameFromFileURL(urlString), Constant.EMPTY_STRING);
            File savedFolder = new File(folderPath);
            savedFolder.mkdirs();
        }

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                mException = new Exception(mContext.getString(R.string.message_invalid_url));
                return;
            }

            InputStream input = connection.getInputStream();
            OutputStream output = new FileOutputStream(saveURL);

            byte datas[] = new byte[4096];
            int count;
            while ((count = input.read(datas)) != -1) {
                if (isCancelled()) {
                    input.close();
                    connection.disconnect();
                    mException = new Exception(mContext.getString(R.string.message_download_cancelled));
                    return;
                }
                output.write(datas, 0, count);
            }
            output.close();
            input.close();
            connection.disconnect();
        } catch (Exception e) {
            mException = e;
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mAudioDownloaderListener != null) {
            if (mException != null) {
                mAudioDownloaderListener.onDownloadAudioFailure(mException.getMessage());
            } else {
                mAudioDownloaderListener.onDownloadAudioSuccess();
            }
        }
    }

    public interface AudioDownloaderListener {
        void onDownloadAudioSuccess();

        void onDownloadAudioFailure(String message);
    }
}