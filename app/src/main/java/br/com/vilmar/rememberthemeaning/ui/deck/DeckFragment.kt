package br.com.vilmar.rememberthemeaning.ui.deck

import androidx.lifecycle.Observer
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.ui.activity.HomeActivity
import br.com.vilmar.rememberthemeaning.ui.cadastreedit.CadastreEditActivity
import com.vilmar.rememberthemeaning.app.R
import com.vilmar.rememberthemeaning.app.databinding.DeckFragmentBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DeckFragment: androidx.fragment.app.Fragment() {

    private val adapter = VocabularyAdapter()

    private lateinit var fragmentBinding : DeckFragmentBinding

    @Inject
    lateinit var viewModel: DeckViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.deck_fragment, container, false)

        fragmentBinding.viewModel = viewModel

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()

        observerVocabularyAdapter()
        observerUIEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        menuInflater!!.inflate(R.menu.toolbar_menu_deck, menu)

        handleSearchView(menu)

        super.onCreateOptionsMenu(menu, menuInflater)
    }

    private fun handleSearchView(menu : Menu?) {
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(text: String): Boolean {
                viewModel.search(text)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }
        })
    }

    private fun setupToolbar() {
        val toolbar = fragmentBinding.includeToolbar as Toolbar

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_deck)
    }

    private fun setupRecyclerView() {
        fragmentBinding.recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, SPAN_COUNT)

        fragmentBinding.recyclerView.adapter = adapter

        adapter.onItemClickVocabulary {
            openEditVocabulary(it)
        }
    }

    private fun observerVocabularyAdapter() {
        viewModel.vocabularyListLiveData.observe(this, Observer {
            val vocabulary = it ?: emptyList()
            adapter.loadData(vocabulary)
        })
    }

    private fun observerUIEvents() {
        viewModel.newWordScreen.observe(this, Observer {
            openNewWordActivity()
        })
    }

    private fun openNewWordActivity() {
        startActivity(Intent(activity, HomeActivity::class.java))
    }

    private fun openEditVocabulary(vocabulary: Vocabulary) {
        val intent = Intent(activity, CadastreEditActivity::class.java)

        intent.putExtra(Vocabulary.WORDBUNDLE, vocabulary)

        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVocabulary()
    }

    companion object {

        const val SPAN_COUNT = 2

    }
}