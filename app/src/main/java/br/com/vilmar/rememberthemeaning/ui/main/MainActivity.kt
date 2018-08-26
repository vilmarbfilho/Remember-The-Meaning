package br.com.vilmar.rememberthemeaning.ui.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.ui.activity.HomeActivity
import com.vilmar.rememberthemeaning.app.R
import com.vilmar.rememberthemeaning.app.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        setupRecyclerView()

        observerVocabulary()
        observerEvents()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = MainViewModel(VocabularyRepository(VocabularyDao(this)))

        binding.viewModel = viewModel
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
    }

    private fun observerVocabulary() {
        viewModel.vocabulary.observe(this, Observer {
            val vocabulary = it ?: emptyList()
            binding.recyclerView.adapter = VocabularyAdapter(vocabulary)
        })
    }

    private fun observerEvents() {
        viewModel.uiEventLiveData.observe(this, Observer {
            when(it) {
                1 -> openNewWordActivity()
            }
        })
    }

    private fun openNewWordActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVocabulary()
    }

    companion object {

        const val SPAN_COUNT = 2

    }

}