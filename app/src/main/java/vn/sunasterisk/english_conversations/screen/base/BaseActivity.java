package vn.sunasterisk.english_conversations.screen.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initComponents();
        registerListeners();
    }

    protected abstract int getContentViewId();

    protected abstract void initComponents();

    protected abstract void registerListeners();
}
