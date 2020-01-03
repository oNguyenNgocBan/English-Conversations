package vn.sunasterisk.english_conversations.screen.sentences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;

public class SentencesAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Sentence> mSentences;
    private Conversation mConversation;

    public SentencesAdapter(Conversation conversation) {
        mConversation = conversation;
    }

    public void setSentences(List<Sentence> sentences) {
        mSentences = sentences;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        switch (viewType) {
            case 0:
                View viewLeft = mLayoutInflater.inflate(
                        R.layout.sentence_item_left,
                        parent,
                        false);
                return new SentenceLeft(viewLeft);
            case 1:
                View viewRight = mLayoutInflater.inflate(
                        R.layout.sentence_item_right,
                        parent,
                        false);
                return new SentenceRight(viewRight);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SentenceViewHolder sententceViewHolder = (SentenceViewHolder) holder;
        sententceViewHolder.display(mSentences.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        return mSentences == null ? 0 : mSentences.size();
    }

    private class SentenceLeft extends SentenceViewHolder {

        private SentenceLeft(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected String getAvatarUrl() {
            return mConversation.getUserA().getFullAvatarUrl(mConversation.getId());
        }
    }

    public class SentenceRight extends SentenceViewHolder {

        private SentenceRight(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected String getAvatarUrl() {
            return mConversation.getUserB().getFullAvatarUrl(mConversation.getId());
        }
    }

    private abstract class SentenceViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageUserAvatar;
        private ImageView mImageStar;
        private TextView mTextSentence;
        private ImageView mImageSpeaker;

        private SentenceViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents();
            registerListeners();
        }

        private void registerListeners() {
            // TODO handle speaker + avatar icon tapped
        }

        private void initComponents() {
            mImageStar = itemView.findViewById(R.id.image_star);
            mImageUserAvatar = itemView.findViewById(R.id.image_user_avatar);
            mTextSentence = itemView.findViewById(R.id.text_sentence);
            mImageSpeaker = itemView.findViewById(R.id.speaker);
        }

        void display(Sentence sentence) {
            mTextSentence.setText(sentence.getText());
            String url = getAvatarUrl();

            Glide.with(itemView.getContext())
                    .load(url).transform(new CircleCrop())
                    //.apply(RequestOptions.circleCropTransform())
                    .into(mImageUserAvatar);
            // TODO display score in mImageStar
        }

        protected abstract String getAvatarUrl();
    }
}
