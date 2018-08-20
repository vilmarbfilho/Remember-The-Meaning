package br.com.vilmar.rememberthemeaning.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.vilmar.rememberthemeaning.database.domain.Word
import com.vilmar.rememberthemeaning.app.R

class VocabularyAdapter(var vocabulary: List<Word>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VocabularyViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.word_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val binding = (holder as VocabularyViewHolder).binding
        binding?.word = vocabulary[position]
        binding?.executePendingBindings()
    }

    override fun getItemCount() = vocabulary.size
}