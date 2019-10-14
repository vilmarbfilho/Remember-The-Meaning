package br.com.vilmar.rememberthemeaning.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vilmar.rememberthemeaning.app.R;

import java.util.List;

import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary;
import br.com.vilmar.rememberthemeaning.util.HelperUtil;

/**
 * Created by vilmar on 25/06/14.
 */
public class VocabularyAdapter extends ArrayAdapter<Vocabulary> {

    private Context context;
    private int resource;

    public VocabularyAdapter(Context context, int resource, List<Vocabulary> objects) {
        super(context, resource, objects);

        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            convertView.setTag(holder);

            holder.language = (TextView) convertView.findViewById(R.id.tv_language);
            holder.source = (TextView) convertView.findViewById(R.id.tv_source);
            holder.result = (TextView) convertView.findViewById(R.id.tv_result);
            holder.letter = (TextView) convertView.findViewById(R.id.letter);
            holder.ll = (LinearLayout) convertView.findViewById(R.id.container_letter);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Vocabulary vocabulary = getItem(position);

        if(vocabulary.getLanguage() != null){
            String nameLanguage = vocabulary.getLanguage().getName();
            holder.language.setVisibility(View.VISIBLE);
            holder.language.setText((nameLanguage.length() > 30) ? nameLanguage.substring(0,30) + "..." : nameLanguage );
        }

        String word = vocabulary.getWord().substring(0,1).toUpperCase();

        holder.source.setText((vocabulary.getWord().length() > 30) ? vocabulary.getWord().substring(0,30) + "..." : vocabulary.getWord() );
        holder.result.setText((vocabulary.getMeaning().length() > 30) ? vocabulary.getMeaning().substring(0,30) + "..." : vocabulary.getMeaning() );
        holder.letter.setText(word);
        holder.ll.setBackgroundResource(HelperUtil.getColor(word));

        return convertView;
    }

    private class ViewHolder {
        private TextView language;
        private TextView source;
        private TextView result;
        private TextView letter;
        private LinearLayout ll;
    }


}
