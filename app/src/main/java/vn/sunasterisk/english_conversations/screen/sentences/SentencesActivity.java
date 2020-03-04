package vn.sunasterisk.english_conversations.screen.sentences;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;
import vn.sunasterisk.english_conversations.screen.base.BaseActivity;
import vn.sunasterisk.english_conversations.screen.conversations.ConversationsActivity;
import vn.sunasterisk.english_conversations.screen.result.SpeechResultDialog;
import vn.sunasterisk.english_conversations.utils.AudioDownloader;
import vn.sunasterisk.english_conversations.utils.TimeUtils;

public class SentencesActivity extends BaseActivity
        implements SentencesContract.View,
        AudioDownloader.AudioDownloaderListener, SentencesAdapter.SentenceClickListener,
        View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public static Intent getIntent(Context context, Conversation conversation) {
        Intent intent = new Intent(context, SentencesActivity.class);
        intent.putExtra(ConversationsActivity.CONVERSATION_NAME, conversation);
        return intent;
    }

    private static final int REQ_CODE_SPEECH_INPUT = 1;

    private SentencesPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private SentencesAdapter mSentencesAdapter;
    private ProgressBar mProgressBar;
    private ImageView mPlayImageView;
    private TextView mTextCurrentProgress;
    private TextView mTextTotalTime;
    private SeekBar mSeekBar;

    private MediaPlayer mMediaPlayer;

    private Conversation mConversation;
    private Sentence mSentenceClicked;
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sentences;
    }

    @Override
    protected void initComponents() {
        mProgressBar = findViewById(R.id.progress_loading);
        mRecyclerView = findViewById(R.id.recycler_sentences);
        mPlayImageView = findViewById(R.id.image_play);
        mTextCurrentProgress = findViewById(R.id.text_current_progress);
        mTextTotalTime = findViewById(R.id.text_total_time);
        mSeekBar = findViewById(R.id.seek_bar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        Conversation conversation = (Conversation) intent
                .getSerializableExtra(ConversationsActivity.CONVERSATION_NAME);
        mConversation = conversation;
        if (conversation == null) {
            return;
        }
        setTitle(conversation.getTitle());

        mSentencesAdapter = new SentencesAdapter(conversation, this);
        mRecyclerView.setAdapter(mSentencesAdapter);

        mPresenter = new SentencesPresenter(this, conversation);
        mPresenter.getSentences();
        mPresenter.downloadAudio();

        showLoading();
    }

    @Override
    protected void registerListeners() {
        mPlayImageView.setOnClickListener(this);
    }

    private void showLoading() {
        mPlayImageView.setVisibility(View.INVISIBLE);
        mTextCurrentProgress.setVisibility(View.INVISIBLE);
        mTextTotalTime.setVisibility(View.INVISIBLE);
        mSeekBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mPlayImageView.setVisibility(View.VISIBLE);
        mTextCurrentProgress.setVisibility(View.VISIBLE);
        mTextTotalTime.setVisibility(View.VISIBLE);
        mSeekBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void initMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setDataSource(mConversation.getPlayAudioURL());
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        onCompletePlayConversation();
                    }
                });
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        updateDisplayTimeAndSeekBar();
                    }
                });
                mMediaPlayer.prepareAsync();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupSeekBar() {
        mSeekBar.setProgress(0);
        mSeekBar.setMax(Constant.ONE_HUNDRED);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void updateDisplayTimeAndSeekBar() {
        long currentDuration = mMediaPlayer.getCurrentPosition();
        long totalDuration = mMediaPlayer.getDuration();
        long elapsedDuration = totalDuration - currentDuration;

        mTextCurrentProgress.setText(TimeUtils.milliSecondsToTimer(currentDuration));
        mTextTotalTime.setText(TimeUtils.milliSecondsToTimer(elapsedDuration));

        int progress = (int) (TimeUtils.getProgressPercentage(currentDuration,
                totalDuration));
        mSeekBar.setProgress(progress);
    }

    private void setupRunnable() {
        mUpdateTimeTask = new Runnable() {
            public void run() {
                updateDisplayTimeAndSeekBar();
                if (mMediaPlayer.isPlaying()) {
                    updateProgressBar();
                }
            }
        };
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
        setupSeekBar();
        initMediaPlayer();
        setupRunnable();
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, Constant.TIMER_DELAY);
    }

    @Override
    public void onDownloadAudioFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSpeechInput(String message) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.message_let_say) + message);
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
                    List<String> results = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (results != null && results.size() > 0) {
                        String userSpeech = results.get(0);
                        int score = mPresenter.markScoreForSentence(userSpeech, mSentenceClicked);
                        mSentencesAdapter.notifyDataSetChanged();
                        showDialog(userSpeech, score);
                    } else {
                        Toast.makeText(this,
                                getString(R.string.message_does_not_recognize_voice),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }

    public void showDialog(String textUserSpeech, int score) {
        final SpeechResultDialog speechResultDialog = new SpeechResultDialog(
                this,
                mSentenceClicked.getText(),
                textUserSpeech,
                score);
        speechResultDialog.show();
    }

    @Override
    public void onAvatarClicked(Sentence sentence) {
        mSentenceClicked = sentence;
        showSpeechInput(sentence.getText());
    }

    private void onCompletePlayConversation() {
        mPlayImageView.setImageResource(R.drawable.ic_play);
        mSeekBar.setProgress(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_play:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mPlayImageView.setImageResource(R.drawable.ic_play);
                } else {
                    mMediaPlayer.start();
                    mPlayImageView.setImageResource(R.drawable.ic_pause);
                    updateProgressBar();
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mMediaPlayer.getDuration();
        int currentPosition = TimeUtils.progressToTimer(seekBar.getProgress(), totalDuration);

        mMediaPlayer.seekTo(currentPosition);

        updateProgressBar();
    }
}
