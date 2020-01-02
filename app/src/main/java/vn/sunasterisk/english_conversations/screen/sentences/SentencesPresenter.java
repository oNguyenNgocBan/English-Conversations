package vn.sunasterisk.english_conversations.screen.sentences;

import vn.sunasterisk.english_conversations.data.model.Conversation;

public class SentencesPresenter implements SentencesContract.Presenter {

    private SentencesContract.View mView;
    private Conversation mConversation;

    public SentencesPresenter(SentencesContract.View view, Conversation conversation) {
        mView = view;
        mConversation = conversation;
    }

    @Override
    public void getSentences() {

    }
}
