package vn.sunasterisk.english_conversations.data.repository;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;
import vn.sunasterisk.english_conversations.data.source.local.FetchLocalDataSource;
import vn.sunasterisk.english_conversations.data.source.remote.FetchRemoteDataSource;
import vn.sunasterisk.english_conversations.utils.StorageManager;

public class CategoryRepository {

    private static CategoryRepository sInstance;
    private FetchRemoteDataSource mFetchRemoteDataSource;
    private FetchLocalDataSource mFetchLocalDataSource;

    private CategoryRepository(FetchRemoteDataSource fetchRemoteDataSource,
                               FetchLocalDataSource fetchLocalDataSource) {
        mFetchRemoteDataSource = fetchRemoteDataSource;
        mFetchLocalDataSource = fetchLocalDataSource;
    }

    public static CategoryRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CategoryRepository(FetchRemoteDataSource.getInstance(),
                    FetchLocalDataSource.getInstance());
        }
        return sInstance;
    }

    public void getCategories(CategoryDataSource.OnFetchCategoryListener listener) {
        StorageManager storageManager = StorageManager.getInstance();
        if (storageManager.isExistFile(Constant.DATA_JSON_NAME)) {
            mFetchLocalDataSource.getCategories(listener);
        } else {
            mFetchRemoteDataSource.getCategories(listener);
        }
    }
}
