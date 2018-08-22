package br.com.vilmar.rememberthemeaning.ui.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.vilmar.rememberthemeaning.database.dao.VocabularyDao
import br.com.vilmar.rememberthemeaning.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.ui.activity.HomeActivity
import com.vilmar.rememberthemeaning.app.R
import com.vilmar.rememberthemeaning.app.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        setupObserver()
        setupRecyclerView()

    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = MainViewModel(VocabularyRepository(VocabularyDao(this)))

        binding.viewModel = viewModel
    }

    private fun setupObserver() {
        viewModel.uiEventLiveData.observe(this, Observer {
            when(it) {
                1 -> openNewWordActivity()
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = VocabularyAdapter(viewModel.getVocabulary())
    }

    private fun openNewWordActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

}