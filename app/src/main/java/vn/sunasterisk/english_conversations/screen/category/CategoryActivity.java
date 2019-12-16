package vn.sunasterisk.english_conversations.screen.category;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Category;
import vn.sunasterisk.english_conversations.screen.base.BaseActivity;

public class CategoryActivity extends BaseActivity implements CategoryContract.View {

    private RecyclerView mRecyclerView;
    private CategoryPresenter mCategoryPresenter;
    private CategoryAdapter mCategoryAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_category;
    }

    @Override
    protected void initComponents() {
        mRecyclerView = findViewById(R.id.category_recycler);

        mCategoryAdapter = new CategoryAdapter();
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
}
