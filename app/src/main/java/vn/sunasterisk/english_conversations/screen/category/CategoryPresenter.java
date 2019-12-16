package vn.sunasterisk.english_conversations.screen.category;

import java.util.List;

import vn.sunasterisk.english_conversations.data.model.Category;
import vn.sunasterisk.english_conversations.data.repository.CategoryRepository;
import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;

public class CategoryPresenter implements CategoryContract.Presenter,
        CategoryDataSource.OnFetchCategoryListener<Category> {

    private CategoryContract.View mView;
    private CategoryRepository mCategoryRepository;

    public CategoryPresenter(CategoryContract.View view) {
        mView = view;
        mCategoryRepository = CategoryRepository.getInstance();
    }

    @Override
    public void getCategories() {
        mCategoryRepository.getCategories(this);
    }

    @Override
    public void onFetchCategorySuccess(List<Category> data) {
        mView.onGetCategoriesSuccess(data);
    }

    @Override
    public void onFetchCategoryFailure(String message) {
        mView.onGetCategoryFailure(message);
    }
}
