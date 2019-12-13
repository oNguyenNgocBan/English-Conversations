package vn.sunasterisk.english_conversations.data.source.local;

import android.content.Context;

import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;

public class FetchLocalDataSource implements CategoryDataSource.FetchCategoryDataSource {

    private static FetchLocalDataSource sInstance;

    public FetchLocalDataSource() {
    }

    public static FetchLocalDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new FetchLocalDataSource();
        }
        return sInstance;
    }


    @Override
    public void getCategories(CategoryDataSource.OnFetchCategoryListener listener) {
        FetchCategoryFromLocal fetchCategoryFromLocal =
                new FetchCategoryFromLocal(listener);
        fetchCategoryFromLocal.fetchCategories();
    }
}
