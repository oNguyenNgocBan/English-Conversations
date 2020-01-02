package vn.sunasterisk.english_conversations.screen.sentences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Sentence;

public class SentencesAdapter
        extends RecyclerView.Adapter<SentencesAdapter.SententcesViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Sentence> mSentences;

    public SentencesAdapter() {
    }

    public void setSentences(List<Sentence> sentences) {
        mSentences = sentences;
    }

    @NonNull
    @Override
    public SententcesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.sentence_item_left,parent, false);
        return new SententcesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SententcesViewHolder holder, int position) {
        holder.display(mSentences.get(position), position % 2 == 0);
    }

    @Override
    public int getItemCount() {
        return mSentences == null ? 0 : mSentences.size();
    }

    public class SententcesViewHolder extends RecyclerView.ViewHolder {

        // TODO init components + display chat box

        public SententcesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void display(Sentence sentence, Boolean isLeftMessage) {

        }
    }
}
