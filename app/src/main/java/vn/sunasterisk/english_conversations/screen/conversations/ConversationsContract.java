package vn.sunasterisk.english_conversations.screen.conversations;

import java.util.List;

import vn.sunasterisk.english_conversations.data.model.Conversation;

public class ConversationsContract {

    interface View {
        void onGetConversationSuccess(List<Conversation> conversations);

        void onGetConversationFailure(String message);
    }

    interface Presenter {
        void getConversations();
    }
}
