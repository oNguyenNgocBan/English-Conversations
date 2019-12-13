package vn.sunasterisk.english_conversations.data.source;

import java.util.List;

public class CategoryDataSource {

    public interface OnFetchCategoryListener<T> {
        void onFetchCategorySuccess(List<T> data);

        void onFetchCategoryFailure(String message);
    }

    // use for both remote + local datasource
    public interface FetchCategoryDataSource {
        void getCategories(CategoryDataSource.OnFetchCategoryListener listener);
    }
}
