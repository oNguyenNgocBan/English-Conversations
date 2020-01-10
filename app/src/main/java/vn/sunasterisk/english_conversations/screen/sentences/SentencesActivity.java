package vn.sunasterisk.english_conversations.screen.sentences;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Category;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;
import vn.sunasterisk.english_conversations.screen.base.BaseActivity;
import vn.sunasterisk.english_conversations.screen.conversations.ConversationsActivity;
import vn.sunasterisk.english_conversations.utils.AudioDownloader;

public class SentencesActivity extends BaseActivity
        implements SentencesContract.View,
        AudioDownloader.AudioDownloaderListener {

    private static final int REQ_CODE_SPEECH_INPUT = 1;

    public static Intent getIntent(Context context, Conversation conversation) {
        Intent intent = new Intent(context, SentencesActivity.class);
        intent.putExtra(ConversationsActivity.CONVERSATION_NAME, conversation);
        return intent;
    }

    private SentencesPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private SentencesAdapter mSentencesAdapter;

    private AudioDownloader mAudioDownloader;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sentences;
    }

    @Override
    protected void initComponents() {
        mRecyclerView = findViewById(R.id.recycler_sentences);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        Conversation conversation = (Conversation) intent.getSerializableExtra(ConversationsActivity.CONVERSATION_NAME);
        if (conversation == null) {
            return;
        }
        setTitle(conversation.getTitle());

        mAudioDownloader = new AudioDownloader(this);
        mAudioDownloader.execute(conversation);

        mSentencesAdapter = new SentencesAdapter(conversation);
        mRecyclerView.setAdapter(mSentencesAdapter);

        mPresenter = new SentencesPresenter(this, conversation);
        mPresenter.getSentences();
    }

    @Override
    protected void registerListeners() {
    }

    @Override
    public void onGetSentencesSuccess(List<Sentence> sentences) {
        mSentencesAdapter.setSentences(sentences);
        mSentencesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetSentencesFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadSuccess() {
        Log.d("---", "onDownloadSuccess: ");
    }

    @Override
    public void onDownloadFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}