package vn.sunasterisk.english_conversations.data.source.local;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;
import vn.sunasterisk.english_conversations.utils.ParseCategoryFromJSON;
import vn.sunasterisk.english_conversations.utils.StorageManager;

public class FetchCategoryFromLocal {

    private CategoryDataSource.OnFetchCategoryListener mListener;

    public FetchCategoryFromLocal(CategoryDataSource.OnFetchCategoryListener listener) {
        mListener = listener;
    }

    void fetchCategories() {
        StorageManager storageManager = new StorageManager();
        try {
            if (storageManager.isExistFile(Constant.DATA_JSON_NAME)) {
                String jsonData = storageManager.getStringContentOfFile(Constant.DATA_JSON_NAME);
                ParseCategoryFromJSON parseCategoryFromJSON = new ParseCategoryFromJSON(jsonData);
                mListener.onFetchCategorySuccess(parseCategoryFromJSON.getListCategory());
            } else {
                mListener.onFetchCategoryFailure("Data local not exist");
            }
        } catch (IOException | JSONException e) {
            mListener.onFetchCategoryFailure(e.getMessage());
        }
    }
}
