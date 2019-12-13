package vn.sunasterisk.english_conversations.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import vn.sunasterisk.english_conversations.MainApplication;

public class StorageManager {

    private Context mContext;

    public StorageManager() {
        mContext = MainApplication.getInstance().getApplicationContext();
    }

    public void writeStringToInternalStorage(String contentFile, String fileName) {
        FileOutputStream outputStream;
        try {
            outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(contentFile.getBytes());
            outputStream.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringContentOfFile(String fileName) throws IOException {
        if (!isExistFile(fileName)) {
            return null;
        }

        FileInputStream fis = mContext.openFileInput(fileName);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public boolean isExistFile(String fileName) {
        String filePath = mContext.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(filePath);
        return file.exists();
    }
}
