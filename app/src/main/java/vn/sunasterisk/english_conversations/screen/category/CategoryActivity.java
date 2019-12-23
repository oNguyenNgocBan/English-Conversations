package vn.sunasterisk.english_conversations.screen.category;

import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Category;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.screen.base.BaseActivity;
import vn.sunasterisk.english_conversations.screen.conversations.ConversationsActivity;

public class CategoryActivity extends BaseActivity
        implements CategoryContract.View, CategoryAdapter.OnCategoriesClickListener {

    public static final String CATEGORY_NAME = "category";

    private RecyclerView mRecyclerView;
    private CategoryPresenter mCategoryPresenter;
    private CategoryAdapter mCategoryAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_category;
    }

    @Override
    protected void initComponents() {
        mRecyclerView = findViewById(R.id.recycler_categories);

        mCategoryAdapter = new CategoryAdapter(this);
        mRecyclerView.setAdapter(mCategoryAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mCategoryPresenter = new CategoryPresenter(this);
        mCategoryPresenter.getCategories();
    }

    @Override
    protected void registerListeners() {
    }

    @Override
    public void onGetCategoriesSuccess(List<Category> categories) {
        mCategoryAdapter.setCategories(categories);
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetCategoryFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoriesClickListener(Category category) {
        Intent intent = new Intent(this, ConversationsActivity.class);
        intent.putExtra(CATEGORY_NAME, category);
        startActivity(intent);
    }
}
