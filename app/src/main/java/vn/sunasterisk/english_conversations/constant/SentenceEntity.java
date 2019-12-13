package vn.sunasterisk.english_conversations.constant;

import androidx.annotation.StringDef;

@StringDef({
        SentenceEntity.TEXT,
        SentenceEntity.AUDIO
})

public @interface SentenceEntity {
    String TEXT = "text";
    String AUDIO = "audio";
}
