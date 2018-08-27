package br.com.vilmar.rememberthemeaning.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.vilmar.rememberthemeaning.domain.Word
import com.vilmar.rememberthemeaning.app.R

class VocabularyAdapter(var wordList: List<Word>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var listener: OnItemClickVocabularyAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VocabularyViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.word_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val binding = (holder as VocabularyViewHolder).binding
        val word = wordList[position]

        binding?.word = word

        binding?.containerCard?.setOnClickListener {
            listener.onClick(it, word)
        }

        binding?.executePendingBindings()
    }

    override fun getItemCount() = wordList.size

    fun setOnItemClickVocabularyAdapter(listener: OnItemClickVocabularyAdapter) {
        this.listener = listener
    }

    interface OnItemClickVocabularyAdapter {

        fun onClick(view: View, word: Word)

    }
}