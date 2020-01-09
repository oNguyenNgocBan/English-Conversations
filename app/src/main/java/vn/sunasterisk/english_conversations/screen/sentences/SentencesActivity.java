package vn.sunasterisk.english_conversations.screen.sentences;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

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
        implements SentencesContract.View, SentencesAdapter.SentenceClickListener,
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

        mSentencesAdapter = new SentencesAdapter(conversation, this);
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
    public void onSpeakSentenceClicked(Sentence sentence) {

    }

    @Override
    public void onAvatarSentenceClicked(Sentence sentence) {
        promptSpeechInput();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "ahihi do ngoc");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.message_speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    List<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(this, result.get(0), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
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