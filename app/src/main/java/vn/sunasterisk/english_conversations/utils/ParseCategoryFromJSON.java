package vn.sunasterisk.english_conversations.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.sunasterisk.english_conversations.constant.CategoryEntity;
import vn.sunasterisk.english_conversations.constant.ConversationEntity;
import vn.sunasterisk.english_conversations.constant.SentenceEntity;
import vn.sunasterisk.english_conversations.constant.UserEntity;
import vn.sunasterisk.english_conversations.data.model.Category;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;
import vn.sunasterisk.english_conversations.data.model.User;

public class ParseCategoryFromJSON {

    private String mDataJSON;

    public ParseCategoryFromJSON(String dataJSON) {
        mDataJSON = dataJSON;
    }

    public List<Category> getListCategory() throws JSONException  {
        List<Category> categories = new ArrayList<>();
        JSONArray arrayDatas = new JSONArray(mDataJSON);

        for (int i = 0; i < arrayDatas.length(); i++) {
            JSONObject jsonObjectCategory = arrayDatas.getJSONObject(i);
            String categoryID = jsonObjectCategory.getString(CategoryEntity.ID);
            String categoryTitle = jsonObjectCategory.getString(CategoryEntity.TITLE);
            String categoryCover = jsonObjectCategory.getString(CategoryEntity.COVER);

            JSONArray categoryConversationsJSON = jsonObjectCategory.getJSONArray(CategoryEntity.CONVERSATIONS);
            List<Conversation> conversations = new ArrayList<>();
            for (int j = 0; j < categoryConversationsJSON.length(); j++) {
                JSONObject jsonObjectConversation = categoryConversationsJSON.getJSONObject(j);
                String conversationID = jsonObjectConversation.getString(ConversationEntity.ID);
                String conversationTitle = jsonObjectConversation.getString(ConversationEntity.TITLE);
                String conversationAudio = jsonObjectConversation.getString(ConversationEntity.AUDIO);
                String conversationImageLogo = jsonObjectConversation.getString(ConversationEntity.IMAGE_LOGO);

                JSONObject objectUserA = jsonObjectConversation.getJSONObject(ConversationEntity.USER_A);
                String userAName = objectUserA.getString(UserEntity.NAME);
                String userAAvatar = objectUserA.getString(UserEntity.AVATAR);
                User userA = new User(userAName, userAAvatar);

                JSONObject objectUserB = jsonObjectConversation.getJSONObject(ConversationEntity.USER_B);
                String userBName = objectUserA.getString(UserEntity.NAME);
                String userBAvatar = objectUserA.getString(UserEntity.AVATAR);
                User userB = new User(userBName, userBAvatar);

                JSONArray sentencesJSON = jsonObjectConversation.getJSONArray(ConversationEntity.SENTENCES);
                List<Sentence> sentences = new ArrayList<>();
                for (int k = 0; k < sentencesJSON.length(); k++) {
                    JSONObject jsonObjectSentence = sentencesJSON.getJSONObject(k);
                    String sentenceText = jsonObjectSentence.getString(SentenceEntity.TEXT);
                    String sentenceAudio = jsonObjectSentence.getString(SentenceEntity.AUDIO);

                    Sentence sentence = new Sentence(sentenceText, sentenceAudio);
                    sentences.add(sentence);
                }

                Conversation conversation = new Conversation(
                        conversationID,
                        conversationTitle,
                        conversationImageLogo,
                        conversationAudio,
                        userA,
                        userB,
                        sentences);
                conversations.add(conversation);
            }

            Category category = new Category(categoryID, categoryTitle, categoryCover, conversations);
            categories.add(category);
        }

        return categories;
    }
}
