package vn.sunasterisk.english_conversations.screen.conversations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.utils.StringUtils;

public class ConversationsAdapter
        extends RecyclerView.Adapter<ConversationsAdapter.ConversationsViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Conversation> mConversations;

    public ConversationsAdapter() {
    }

    public void setConversations(List<Conversation> conversations) {
        mConversations = conversations;
    }

    @NonNull
    @Override
    public ConversationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.conversation_item, parent, false);
        return new ConversationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationsViewHolder holder, int position) {
        holder.display(mConversations.get(position));
    }

    @Override
    public int getItemCount() {
        if (mConversations == null) {
            return 0;
        }
        return mConversations.size();
    }

    protected class ConversationsViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageConversation;
        private TextView mTextProgress;
        private TextView mTextTitleConversation;

        protected ConversationsViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents();
            registerListeners();
        }

        private void initComponents() {
            mImageConversation = itemView.findViewById(R.id.image_conversation);
            mTextProgress = itemView.findViewById(R.id.text_progress);
            mTextTitleConversation = itemView.findViewById(R.id.text_title_conversation);
        }

        private void registerListeners() {
        }

        protected void display(Conversation conversation) {
            String url = conversation.getLogoFullUrl();
            Glide.with(itemView.getContext())
                    .load(url)
                    .placeholder(R.drawable.default_image)
                    .into(mImageConversation);
            mTextTitleConversation.setText(conversation.getTitle());

            // TODO update progress
            int totalStarCount = conversation.getSentences().size() * 5;
            mTextProgress.setText(StringUtils.formatProgress(0, totalStarCount));
        }
    }
}
