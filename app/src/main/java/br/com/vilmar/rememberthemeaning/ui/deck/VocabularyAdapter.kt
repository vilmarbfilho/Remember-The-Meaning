package br.com.vilmar.rememberthemeaning.ui.deck

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import com.vilmar.rememberthemeaning.app.R

class VocabularyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var vocabularyList : List<Vocabulary> = emptyList()

    private lateinit var onItemClick : (Vocabulary) -> Unit


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
            this.onItemClick(vocabulary)
        }

        binding?.executePendingBindings()
    }

    override fun getItemCount() = vocabularyList.size

    fun loadData(data: List<Vocabulary>) {
        vocabularyList = data
        notifyDataSetChanged()
    }

    fun onItemClickVocabulary(onItemClick : (Vocabulary) -> Unit) {
        this.onItemClick = onItemClick
    }
}