package br.com.vilmar.rememberthemeaning.ui.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import br.com.vilmar.rememberthemeaning.database.dao.VocabularyDao
import br.com.vilmar.rememberthemeaning.repository.VocabularyRepository
import com.vilmar.rememberthemeaning.app.R
import com.vilmar.rememberthemeaning.app.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: VocabularyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        setupRecyclerView()

    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = VocabularyViewModel(VocabularyRepository(VocabularyDao(this)))

        binding.viewModel = viewModel
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = VocabularyAdapter(viewModel.getVocabulary())
    }

}