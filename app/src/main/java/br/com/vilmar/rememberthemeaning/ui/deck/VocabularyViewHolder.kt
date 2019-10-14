package br.com.vilmar.rememberthemeaning.ui.deck

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import br.com.vilmar.rememberthemeaning.databinding.ItemWordBinding

class VocabularyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ItemWordBinding? = DataBindingUtil.bind(view)
}