package br.com.vilmar.rememberthemeaning.ui.deck

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import br.com.vilmar.rememberthemeaning.R
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary

class VocabularyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var vocabularyList: List<Vocabulary> = emptyList()

    private lateinit var onItemClick: (Int, Vocabulary) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        VocabularyViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_word, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as VocabularyViewHolder).binding
        val vocabulary = vocabularyList[position]

        binding?.vocabulary = vocabulary

        binding?.containerCard?.setOnClickListener {
            this.onItemClick(position, vocabulary)
        }

        binding?.executePendingBindings()
    }

    override fun getItemCount() = vocabularyList.size

    fun loadData(data: List<Vocabulary>) {
        vocabularyList = data
        notifyDataSetChanged()
    }

    fun onItemClickVocabulary(onItemClick: (Int, Vocabulary) -> Unit) {
        this.onItemClick = onItemClick
    }

    companion object {
        @JvmStatic
        @BindingAdapter("vocabularyList")
        fun RecyclerView.bindList(items: List<Vocabulary>) {
            (adapter as? VocabularyAdapter)?.loadData(items)
        }
    }
}

