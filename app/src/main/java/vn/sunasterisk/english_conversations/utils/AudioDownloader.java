package vn.sunasterisk.english_conversations.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import vn.sunasterisk.english_conversations.data.model.Conversation;

public class AudioDownloader extends AsyncTask<Conversation, Integer, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;
    private AudioDownloaderListener mAudioDownloaderListener;
    private Exception mException;

    public AudioDownloader(Context context) {
        this.context = context;
        if (context instanceof AudioDownloaderListener) {
            mAudioDownloaderListener = (AudioDownloaderListener) context;
        }
    }

    @Override
    protected String doInBackground(Conversation... conversations) {



        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                mException = new Exception("Invalid URL");
                return null;
            }

            int fileLength = connection.getContentLength();

            input = connection.getInputStream();
            output = new FileOutputStream("/sdcard/file_name.extension");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            mException = e;
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
                mException = ignored;
            }
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mException != null) {
            mAudioDownloaderListener.onDownloadFailure(mException.getMessage());
        } else if (mAudioDownloaderListener != null) {
            mAudioDownloaderListener.onDownloadSuccess();
        }
    }

    interface AudioDownloaderListener {
        void onDownloadSuccess();

        void onDownloadFailure(String message);
    }
}