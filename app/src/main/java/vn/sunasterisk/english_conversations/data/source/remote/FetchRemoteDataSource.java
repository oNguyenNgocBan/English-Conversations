package vn.sunasterisk.english_conversations.data.source.remote;

import android.content.Context;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;

public class FetchRemoteDataSource implements CategoryDataSource.FetchCategoryDataSource {
    private static FetchRemoteDataSource sInstance;

    public FetchRemoteDataSource() {
    }

    public static FetchRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new FetchRemoteDataSource();
        }
        return sInstance;
    }

    public void getCategories(CategoryDataSource.OnFetchCategoryListener listener) {
        FetchCategoryFromURL fetchCategoryFromURL = new FetchCategoryFromURL(listener);
        fetchCategoryFromURL.execute(Constant.BASE_URL);
    }
}
