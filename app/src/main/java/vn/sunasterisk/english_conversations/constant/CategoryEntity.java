package vn.sunasterisk.english_conversations.constant;

import androidx.annotation.StringDef;

@StringDef({
        CategoryEntity.ID,
        CategoryEntity.TITLE,
        CategoryEntity.COVER,
        CategoryEntity.CONVERSATIONS
})

public @interface CategoryEntity {
    String ID = "id";
    String TITLE = "title";
    String COVER = "cover";
    String CONVERSATIONS = "conversations";
}
