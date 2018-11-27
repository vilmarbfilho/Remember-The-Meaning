package br.com.vilmar.rememberthemeaning.ui.deck

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.vilmar.rememberthemeaning.app.databinding.WordItemBinding

class VocabularyViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val binding: WordItemBinding? = DataBindingUtil.bind(view)

}