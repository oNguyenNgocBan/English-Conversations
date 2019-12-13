package vn.sunasterisk.english_conversations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Category;
import vn.sunasterisk.english_conversations.data.repository.CategoryRepository;
import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;

public class MainActivity extends AppCompatActivity
        implements CategoryDataSource.OnFetchCategoryListener<Category> {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO delete: this code is test repository
        CategoryRepository categoryRepository = CategoryRepository.getInstance();
        categoryRepository.getCategories(this);
    }

    @Override
    public void onFetchCategorySuccess(List<Category> datas) {
        Log.d(TAG, "onFetchCategorySuccess: categories count " + datas.size());
    }

    @Override
    public void onFetchCategoryFailure(String message) {
        Log.d(TAG, "onFetchCategoryFailure: " + message);
    }
}
