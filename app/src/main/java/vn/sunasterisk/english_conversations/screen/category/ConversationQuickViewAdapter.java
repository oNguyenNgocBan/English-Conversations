package vn.sunasterisk.english_conversations.screen.category;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Conversation;

public class ConversationQuickViewAdapter
        extends RecyclerView.Adapter<ConversationQuickViewAdapter.ConversationQuickViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Conversation> mConversations;

    public ConversationQuickViewAdapter() {
    }

    public void setConversations(List<Conversation> conversations) {
        mConversations = conversations;
    }

    @NonNull
    @Override
    public ConversationQuickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.conversation_quick_view_item,
                parent,
                false);
        return new ConversationQuickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationQuickViewHolder holder, int position) {
        holder.display(mConversations.get(position));
    }

    @Override
    public int getItemCount() {
        return mConversations.size();
    }

    protected class ConversationQuickViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        private ConversationQuickViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents();
            registerListeners();
        }

        private void initComponents() {
            mImageView = itemView.findViewById(R.id.imageView);
        }

        private void registerListeners() {
            // TODO handle click conversation item
        }

        private void display(Conversation conversation) {
            Glide.with(itemView.getContext())
                    .load(conversation.getLogoFullUrl())
                    .placeholder(R.drawable.default_image)
                    .into(mImageView);
        }
    }
}
