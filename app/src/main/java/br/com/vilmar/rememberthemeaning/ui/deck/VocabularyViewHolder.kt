package br.com.vilmar.rememberthemeaning.ui.deck

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.vilmar.rememberthemeaning.app.databinding.WordItemBinding

class VocabularyViewHolder(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    val binding: WordItemBinding? = DataBindingUtil.bind(view)

}