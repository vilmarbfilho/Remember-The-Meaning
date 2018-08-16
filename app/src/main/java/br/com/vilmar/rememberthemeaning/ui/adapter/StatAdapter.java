package br.com.vilmar.rememberthemeaning.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vilmar.rememberthemeaning.app.R;

import java.util.List;

import br.com.vilmar.rememberthemeaning.database.model.Vocabulary;

/**
 * Created by vilmar on 03/04/15.
 */
public class StatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<Vocabulary> listVocabularies;

    public StatAdapter(Activity mActivity, List<Vocabulary> listVocabularies){
        this.mActivity = mActivity;
        this.listVocabularies = listVocabularies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_stat_word, viewGroup, false);

        StatViewHolder vh = new StatViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        StatViewHolder statViewHolder = (StatViewHolder) viewHolder;

        Vocabulary vocabulary = listVocabularies.get(position);
        statViewHolder.statWord.setText(vocabulary.getWord());
        statViewHolder.statValue.setText(vocabulary.getStat() + " notifications.");

    }

    @Override
    public int getItemCount() {
        return this.listVocabularies.size();
    }

    public class StatViewHolder extends RecyclerView.ViewHolder{

        public TextView statWord;
        public TextView statValue;

        public StatViewHolder(View v) {
            super(v);

            statWord = (TextView) v.findViewById(R.id.tv_stat_word);
            statValue = (TextView) v.findViewById(R.id.tv_stat_value);
        }
    }
}
