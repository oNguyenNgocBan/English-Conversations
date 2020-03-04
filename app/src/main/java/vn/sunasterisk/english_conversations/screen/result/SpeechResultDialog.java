package vn.sunasterisk.english_conversations.screen.result;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.constant.Constant;

public class SpeechResultDialog extends Dialog {

    private String mCorrectAnswer;
    private String mUserSpeech;
    private int mScore;

    private ImageView mImageSmile;
    private TextView mTextSpeechResult;
    private TextView mTextCorrectAnswer;
    private TextView mTextUserSpeech;
    private Button buttonOk;

    public SpeechResultDialog(@NonNull Context context,
                              String textCorrectAnswer,
                              String textUserSpeech,
                              int score) {
        super(context);
        mCorrectAnswer = textCorrectAnswer;
        mUserSpeech = textUserSpeech;
        mScore = score;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_result_dialog);
        initComponents();
        registerListeners();
        displayResult();
    }

    private void displayResult() {
        mTextCorrectAnswer.setText(mCorrectAnswer);
        mTextUserSpeech.setText(mUserSpeech);

        if (mScore >= Constant.VERY_GOOD_SCORE) {
            mImageSmile.setImageResource(R.drawable.very_good);
            mTextSpeechResult.setText(getContext().getString(R.string.title_very_good));
            int veryGoodColor = getContext().getResources().getColor(R.color.color_very_good);
            mTextSpeechResult.setTextColor(veryGoodColor);
            mTextUserSpeech.setTextColor(veryGoodColor);
            buttonOk.setBackgroundColor(veryGoodColor);
        } else if (mScore >= Constant.GOOD_SCORE) {
            mImageSmile.setImageResource(R.drawable.good);
            mTextSpeechResult.setText(getContext().getString(R.string.title_good));
            int goodColor = getContext().getResources().getColor(R.color.color_good);
            mTextSpeechResult.setTextColor(goodColor);
            mTextUserSpeech.setTextColor(goodColor);
            buttonOk.setBackgroundColor(goodColor);
        } else {
            mImageSmile.setImageResource(R.drawable.bad);
            mTextSpeechResult.setText(getContext().getString(R.string.title_bad));
            int badColor = getContext().getResources().getColor(R.color.color_bad);
            mTextSpeechResult.setTextColor(badColor);
            mTextUserSpeech.setTextColor(badColor);
            buttonOk.setBackgroundColor(badColor);
        }
    }

    private void registerListeners() {
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initComponents() {
        mImageSmile = findViewById(R.id.image_smile);
        mTextSpeechResult = findViewById(R.id.text_speech_result);
        mTextCorrectAnswer = findViewById(R.id.text_answer);
        mTextUserSpeech = findViewById(R.id.text_user_speech);
        buttonOk = findViewById(R.id.button_ok);
    }
}
