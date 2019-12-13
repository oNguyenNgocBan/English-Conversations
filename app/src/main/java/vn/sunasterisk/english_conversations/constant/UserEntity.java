package vn.sunasterisk.english_conversations.constant;

import androidx.annotation.StringDef;

@StringDef({
        UserEntity.NAME,
        UserEntity.AVATAR
})

public @interface UserEntity {
    String NAME = "name";
    String AVATAR = "avatar";
}
