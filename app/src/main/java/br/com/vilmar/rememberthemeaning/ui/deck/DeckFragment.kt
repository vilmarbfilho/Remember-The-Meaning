package br.com.vilmar.rememberthemeaning.ui.deck

import androidx.lifecycle.Observer
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import br.com.vilmar.rememberthemeaning.R
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.databinding.FragmentDeckBinding
import br.com.vilmar.rememberthemeaning.ui.activity.HomeActivity
import br.com.vilmar.rememberthemeaning.ui.cadastreedit.CadastreEditActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeckFragment : Fragment() {

    val deckViewModel: DeckViewModel by viewModel()

    private val adapter = VocabularyAdapter()

    private lateinit var fragmentBinding: FragmentDeckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = DataBindingUtil.inflate<FragmentDeckBinding>(
            inflater,
            R.layout.fragment_deck,
            container,
            false
        ).apply {
            viewModel = this@DeckFragment.deckViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()

        observerUIEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu_deck, menu)

        handleSearchView(menu)

        super.onCreateOptionsMenu(menu, menuInflater)
    }

    private fun handleSearchView(menu: Menu) {
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                deckViewModel.search(text)
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
        fragmentBinding.recyclerView.layoutManager = GridLayoutManager(activity, SPAN_COUNT)

        fragmentBinding.recyclerView.adapter = adapter

        adapter.onItemClickVocabulary { position, _ ->
            deckViewModel.onItemClickVocabulary(position)
        }
    }

    private fun observerUIEvents() {
        deckViewModel.newWord.observe(this, Observer {
            openNewWordActivity()
        })

        deckViewModel.editWord.observe(this, Observer {
            openEditVocabulary(it)
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
        deckViewModel.getVocabulary()
    }

    companion object {
        const val SPAN_COUNT = 2
    }
}
