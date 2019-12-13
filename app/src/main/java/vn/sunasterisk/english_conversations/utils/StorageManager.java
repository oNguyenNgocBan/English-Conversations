package vn.sunasterisk.english_conversations.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import vn.sunasterisk.english_conversations.constant.Constant;

public class StorageManager {

    private static StorageManager sInstance;
    private Context mContext;

    public static void init(Context context) {
        if (sInstance == null) {
            sInstance = new StorageManager(context);
        }
    }

    public static StorageManager getInstance() {
        return sInstance;
    }

    private StorageManager(Context context) {
        mContext = context;
    }

    public void writeStringToInternalStorage(String contentFile,
                                             String fileName) throws IOException {
        FileOutputStream outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
        outputStream.write(contentFile.getBytes());
        outputStream.close();
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
        String filePath = mContext.getFilesDir().getAbsolutePath() + Constant.SLASH + fileName;
        File file = new File(filePath);
        return file.exists();
    }
}
