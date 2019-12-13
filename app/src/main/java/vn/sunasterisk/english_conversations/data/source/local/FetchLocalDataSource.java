package vn.sunasterisk.english_conversations.data.source.local;

import android.content.Context;

import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;

public class FetchLocalDataSource implements CategoryDataSource.FetchCategoryDataSource {

    private static FetchLocalDataSource sInstance;
    private Context mContext;

    private FetchLocalDataSource(Context context) {
        mContext = context;
    }

    public static void init(Context context) {
        sInstance = new FetchLocalDataSource(context);
    }

    public static FetchLocalDataSource getInstance() {
        return sInstance;
    }


    @Override
    public void getCategories(CategoryDataSource.OnFetchCategoryListener listener) {
        FetchCategoryFromLocal fetchCategoryFromLocal =
                new FetchCategoryFromLocal(listener);
        fetchCategoryFromLocal.fetchCategories(mContext);
    }
}
