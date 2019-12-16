package vn.sunasterisk.english_conversations.screen.category;

import java.util.List;

import vn.sunasterisk.english_conversations.data.model.Category;

public interface CategoryContract {

    interface View {
        void onGetCategoriesSuccess(List<Category> categories);
        void onGetCategoryFailure(String message);
    }

    interface Presenter {
        void getCategories();
    }
}
