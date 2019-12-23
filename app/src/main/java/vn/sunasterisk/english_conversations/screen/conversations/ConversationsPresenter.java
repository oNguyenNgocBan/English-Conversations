package vn.sunasterisk.english_conversations.screen.conversations;

import android.content.Context;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Category;

public class ConversationsPresenter implements ConversationsContract.Presenter {

    private ConversationsContract.View mView;
    private Category mCategory;

    public ConversationsPresenter(ConversationsContract.View view, Category category) {
        mView = view;
        mCategory = category;
    }

    @Override
    public void getConversations() {
        if (mCategory.getConversations().size() > 0) {
            mView.onGetConversationSuccess(mCategory.getConversations());
        } else {
            Context context = (Context) mView;
            if (context == null) {
                return;
            }
            mView.onGetConversationFailure(context.getString(R.string.conversations_empty));
        }
    }
}
