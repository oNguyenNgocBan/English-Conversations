package vn.sunasterisk.english_conversations;

import android.app.Application;

public class MainApplication extends Application {

    private static MainApplication sApplication;

    public static MainApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.sApplication = this;
    }
}
