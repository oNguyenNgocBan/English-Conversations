package vn.sunasterisk.english_conversations.constant;

import androidx.annotation.StringDef;

@StringDef({
        ConversationEntity.ID,
        ConversationEntity.TITLE,
        ConversationEntity.USER_A,
        ConversationEntity.USER_B,
        ConversationEntity.AUDIO,
        ConversationEntity.SENTENCES,
        ConversationEntity.LOGO_IMAGE
})

public @interface ConversationEntity {
    String ID = "id";
    String TITLE = "title";
    String USER_A = "userA";
    String USER_B = "userB";
    String AUDIO = "audio";
    String SENTENCES = "sentences";
    String LOGO_IMAGE = "imageLogo";
}
