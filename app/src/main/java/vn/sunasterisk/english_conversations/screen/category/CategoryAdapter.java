package vn.sunasterisk.english_conversations.screen.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Category> mCategories;

    public CategoryAdapter() {
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.display(mCategories.get(position));
    }

    @Override
    public int getItemCount() {
        if (mCategories == null) {
            return 0;
        }
        return mCategories.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mCoverImageView;
        private TextView mTitleTextView;
        private RecyclerView mRecyclerViewConversations;
        private ConversationQuickViewAdapter mConversationAdapter;
        private LinearLayoutManager mLinearLayoutManager;
        private Category mCategory;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents();
            registerListeners();
        }

        private void registerListeners() {
            // TODO handle tap to category item
        }

        private void initComponents() {
            mCoverImageView = itemView.findViewById(R.id.image_cover);
            mTitleTextView = itemView.findViewById(R.id.text_title);
            mRecyclerViewConversations = itemView.findViewById(R.id.recycler_conversations);

            mConversationAdapter = new ConversationQuickViewAdapter();
            mRecyclerViewConversations.setAdapter(mConversationAdapter);

            mLinearLayoutManager = new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false);
            mRecyclerViewConversations.setLayoutManager(mLinearLayoutManager);

            // disable user scroll
            mRecyclerViewConversations.setNestedScrollingEnabled(false);

            initTimer();
        }

        private void initTimer() {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    scrollToNextPosition();
                    initTimer();
                }
            };

            // make random time between 1 -> 2.5s
            long delay = ThreadLocalRandom.current().nextLong(1000, 2500);
            new Timer().schedule(timerTask, delay);
        }

        private void scrollToNextPosition() {
            if (mRecyclerViewConversations == null) {
                return;
            }
            if (mCategory == null) {
                return;
            }
            if (mCategory.getConversations().size() == 0) {
                return;
            }
            int currentPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
            if (currentPosition == mCategory.getConversations().size() - 1) {
                mRecyclerViewConversations.smoothScrollToPosition(0);
            } else {
                mRecyclerViewConversations.smoothScrollToPosition(currentPosition + 1);
            }
        }

        private void display(Category category) {
            mCategory = category;
            String url = category.getCoverFullUrl();
             Glide.with(itemView.getContext())
                     .load(url)
                     .placeholder(R.drawable.default_image)
                     .into(mCoverImageView);
            mTitleTextView.setText(category.getTitle());
            mConversationAdapter.setConversations(category.getConversations());
            mConversationAdapter.notifyDataSetChanged();
        }
    }
}
