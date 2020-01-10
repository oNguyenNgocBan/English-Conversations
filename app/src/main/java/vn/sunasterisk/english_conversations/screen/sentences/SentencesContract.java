package vn.sunasterisk.english_conversations.screen.sentences;

import java.util.List;

import vn.sunasterisk.english_conversations.data.model.Sentence;

public interface SentencesContract {

    public interface View {
        void onGetSentencesSuccess(List<Sentence> sentences);

        void onGetSentencesFailure(String message);

        void onDownloadAudioSuccess();

        void onDownloadAudioFailure(String message);
    }

    public interface Presenter {
        void getSentences();

        void downloadAudio();
    }
}
