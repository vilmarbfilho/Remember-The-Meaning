package br.com.vilmar.rememberthemeaning.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vilmar.rememberthemeaning.app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.vilmar.rememberthemeaning.database.model.Media;
import br.com.vilmar.rememberthemeaning.database.model.Vocabulary;

/**
 * Created by vilmar on 2/13/15.
 */
public class RememberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 10;

    private List<Vocabulary> listVocabularies;
    private Context mContext;

    private OnItemClickListener onItemClickListener;

    public RememberAdapter(Context context, List<Vocabulary> listVocabularies){
        mContext = context;
        this.listVocabularies = listVocabularies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_cardview, parent, false);
            EmptyViewHolder evh = new EmptyViewHolder(v);
            return evh;
        }

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_card_view, parent, false);

        return new WordViewHolder(v);
    }

    @Override
    //public void onBindViewHolder(WordViewHolder wordViewHolder, int position) {
    public void onBindViewHolder(RecyclerView.ViewHolder vho, int position) {

        if(vho instanceof EmptyViewHolder){
            EmptyViewHolder empty = (EmptyViewHolder) vho;
            empty.tv_empty.setText(mContext.getString(R.string.empty_card));


        } else  if(vho instanceof WordViewHolder){
            Vocabulary vocabulary = listVocabularies.get(position);
            WordViewHolder vh = (WordViewHolder) vho;

            if(vocabulary.getMediaList().size() > 0){
                Media mediaImage = new ArrayList<Media>(vocabulary.getMediaList()).get(0);

                vh.rl.setVisibility(View.VISIBLE);
                vh.secondDelete.setVisibility(View.GONE);

                Picasso.with(mContext)
                        .load(new File(mediaImage.getPath()))
                        .into(vh.image);

            } else {
                vh.rl.setVisibility(View.GONE);
                vh.secondDelete.setVisibility(View.VISIBLE);
            }

            if(vocabulary.getLanguage() != null){
                vh.language.setText(vocabulary.getLanguage().getName());
            }

            vh.word.setText(vocabulary.getWord());
            vh.meaning.setText(vocabulary.getMeaning());
        }
    }

    @Override
    public int getItemCount() {
        //return listVocabularies.size();
        return listVocabularies.size() > 0 ? listVocabularies.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (listVocabularies.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_empty;

        public EmptyViewHolder(View v) {
            super(v);

            tv_empty = (TextView) v.findViewById(R.id.card_empty);

        }
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RelativeLayout rl;

        public ImageView image;
        public TextView language;
        public TextView word;
        public TextView meaning;

        public ImageButton firstDelete;
        public ImageButton secondDelete;


        public WordViewHolder(View v) {
            super(v);
            rl = (RelativeLayout) v.findViewById(R.id.container_image);

            image = (ImageView) v.findViewById(R.id.iv_image);
            language =  (TextView) v.findViewById(R.id.tv_language);
            word = (TextView)  v.findViewById(R.id.tv_word);
            meaning = (TextView)  v.findViewById(R.id.tv_meaning);

            firstDelete = (ImageButton) v.findViewById(R.id.ib_first_delete);
            secondDelete = (ImageButton) v.findViewById(R.id.ib_second_delete);

            firstDelete.setOnClickListener(this);
            secondDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void add(Vocabulary item, int position) {
        listVocabularies.add(position, item);
        notifyItemInserted(position);
    }

    //public void remove(Word item) {
    public void remove(int position) {
        //int position = listWords.indexOf(item);
        listVocabularies.remove(position);
        notifyItemRemoved(position);
    }


    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

}
