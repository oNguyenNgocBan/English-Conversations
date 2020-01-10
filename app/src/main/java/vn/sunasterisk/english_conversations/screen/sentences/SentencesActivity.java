package vn.sunasterisk.english_conversations.screen.sentences;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;
import vn.sunasterisk.english_conversations.screen.base.BaseActivity;
import vn.sunasterisk.english_conversations.screen.conversations.ConversationsActivity;
import vn.sunasterisk.english_conversations.utils.AudioDownloader;

public class SentencesActivity extends BaseActivity
        implements SentencesContract.View,
        AudioDownloader.AudioDownloaderListener {

    public static Intent getIntent(Context context, Conversation conversation) {
        Intent intent = new Intent(context, SentencesActivity.class);
        intent.putExtra(ConversationsActivity.CONVERSATION_NAME, conversation);
        return intent;
    }

    private SentencesPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private SentencesAdapter mSentencesAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sentences;
    }

    @Override
    protected void initComponents() {
        mProgressBar = findViewById(R.id.progress_loading);
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

        mSentencesAdapter = new SentencesAdapter(conversation);
        mRecyclerView.setAdapter(mSentencesAdapter);

        mPresenter = new SentencesPresenter(this, conversation);
        mPresenter.getSentences();
        mPresenter.downloadAudio();

        showLoading();
    }

    @Override
    protected void registerListeners() {
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
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
    public void onDownloadAudioSuccess() {
        hideLoading();
    }

    @Override
    public void onDownloadAudioFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
