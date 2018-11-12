package br.com.vilmar.rememberthemeaning.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import com.vilmar.rememberthemeaning.app.R

class VocabularyAdapter(var vocabularyList: List<Vocabulary>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var listener: OnItemClickVocabularyAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VocabularyViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.word_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as VocabularyViewHolder).binding
        val vocabulary = vocabularyList[position]

        binding?.vocabulary = vocabulary

        binding?.containerCard?.setOnClickListener {
            listener.onClick(position)
        }

        binding?.executePendingBindings()
    }

    override fun getItemCount() = vocabularyList.size

    fun setOnItemClickVocabularyAdapter(listener: OnItemClickVocabularyAdapter) {
        this.listener = listener
    }

    interface OnItemClickVocabularyAdapter {

        fun onClick(position: Int)

    }
}