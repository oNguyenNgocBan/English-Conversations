package vn.sunasterisk.english_conversations;

import android.app.Application;

import vn.sunasterisk.english_conversations.data.source.local.FetchLocalDataSource;
import vn.sunasterisk.english_conversations.utils.StorageManager;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StorageManager.init(this);
        FetchLocalDataSource.init(this);
    }
}
