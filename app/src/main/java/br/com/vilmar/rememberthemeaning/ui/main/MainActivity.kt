package br.com.vilmar.rememberthemeaning.ui.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.ui.activity.HomeActivity
import br.com.vilmar.rememberthemeaning.ui.cadastreedit.CadastreEditActivity
import br.com.vilmar.rememberthemeaning.ui.main.MainViewModel.Companion.OPEN_NEW_WORD_SCREEN
import com.vilmar.rememberthemeaning.app.R
import com.vilmar.rememberthemeaning.app.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        initBinding()

        setupToolbar()

        setupRecyclerView()

        observerVocabularyAdapter()
        observerEditVocabulary()
        observerUIEvents()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.includeToolbar?.toolbar)
        supportActionBar?.title = getString(R.string.title_main)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
    }

    private fun observerVocabularyAdapter() {
        viewModel.vocabularyListLiveData.observe(this, Observer {
            val vocabulary = it ?: emptyList()
            val adapter = VocabularyAdapter(vocabulary)

            binding.recyclerView.adapter = adapter

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
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun observerEditVocabulary() {
        viewModel.wordEventLiveData.observe(this, Observer {
            val intent = Intent(this, CadastreEditActivity::class.java)

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

    }

}