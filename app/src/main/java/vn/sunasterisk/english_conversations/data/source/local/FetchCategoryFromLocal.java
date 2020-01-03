package vn.sunasterisk.english_conversations.data.source.local;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;
import vn.sunasterisk.english_conversations.utils.ParseCategoryFromJSON;
import vn.sunasterisk.english_conversations.utils.StorageManager;

public class FetchCategoryFromLocal {

    private CategoryDataSource.OnFetchCategoryListener mListener;

    public FetchCategoryFromLocal(CategoryDataSource.OnFetchCategoryListener listener) {
        mListener = listener;
    }

    void fetchCategories(Context context) {
        StorageManager storageManager = StorageManager.getInstance();
        try {
            if (storageManager.isExistFile(Constant.DATA_JSON_NAME)) {
                String jsonData = storageManager.getStringContentOfFile(Constant.DATA_JSON_NAME);
                ParseCategoryFromJSON parseCategoryFromJSON = new ParseCategoryFromJSON(jsonData);
                mListener.onFetchCategorySuccess(parseCategoryFromJSON.getListCategory());
            } else {
                mListener.onFetchCategoryFailure(context.getString(R.string.message_data_local_not_exist));
            }
        } catch (IOException | JSONException e) {
            mListener.onFetchCategoryFailure(e.getMessage());
        }
    }
}
