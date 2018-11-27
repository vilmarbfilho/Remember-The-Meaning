package br.com.vilmar.rememberthemeaning.ui.deck

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.ui.activity.HomeActivity
import br.com.vilmar.rememberthemeaning.ui.cadastreedit.CadastreEditActivity
import br.com.vilmar.rememberthemeaning.ui.common.BaseFragment
import br.com.vilmar.rememberthemeaning.ui.deck.DeckViewModel.Companion.OPEN_NEW_WORD_SCREEN
import com.vilmar.rememberthemeaning.app.R
import com.vilmar.rememberthemeaning.app.databinding.DeckFragmentBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DeckFragment: BaseFragment() {

    private lateinit var fragmentBinding : DeckFragmentBinding

    @Inject
    lateinit var viewModel: DeckViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun fragmentName(): String {
        return "DeckFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.deck_fragment, container, false)

        fragmentBinding.viewModel = viewModel

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        observerVocabularyAdapter()
        observerEditVocabulary()
        observerUIEvents()
    }

    private fun setupRecyclerView() {
        fragmentBinding.recyclerView.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
    }

    private fun observerVocabularyAdapter() {
        viewModel.vocabularyListLiveData.observe(this, Observer {
            val vocabulary = it ?: emptyList()
            val adapter = VocabularyAdapter(vocabulary)

            fragmentBinding.recyclerView.adapter = adapter

            adapter.setOnItemClickVocabularyAdapter(object : VocabularyAdapter.OnItemClickVocabularyAdapter {
                override fun onClick(position: Int) {
                    viewModel.openWordActivity(position)
                }
            })
        })
    }

    private fun observerUIEvents() {
        viewModel.uiEventLiveData.observe(this, Observer {
            when(it) {
                OPEN_NEW_WORD_SCREEN -> openNewWordActivity()
            }
        })
    }

    private fun openNewWordActivity() {
        startActivity(Intent(activity, HomeActivity::class.java))
    }

    private fun observerEditVocabulary() {
        viewModel.wordEventLiveData.observe(this, Observer {
            val intent = Intent(activity, CadastreEditActivity::class.java)

            intent.putExtra(Vocabulary.WORDBUNDLE, it)

            startActivity(intent)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVocabulary()
    }

    companion object {

        const val SPAN_COUNT = 2

        fun newInstance() : DeckFragment {
            return DeckFragment()
        }

    }

}