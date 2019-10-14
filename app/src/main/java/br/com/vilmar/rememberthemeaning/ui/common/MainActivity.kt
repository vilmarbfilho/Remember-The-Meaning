package br.com.vilmar.rememberthemeaning.ui.common

import androidx.lifecycle.Observer
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.core.view.GravityCompat
import br.com.vilmar.rememberthemeaning.R
import br.com.vilmar.rememberthemeaning.databinding.ActivityMainBinding
import br.com.vilmar.rememberthemeaning.ui.activity.HomeActivity
import br.com.vilmar.rememberthemeaning.ui.common.MainViewModel.Companion.OPEN_FEEDBACK_SCREEN
import br.com.vilmar.rememberthemeaning.ui.common.MainViewModel.Companion.OPEN_SETTINGS_SCREEN
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainActivity
        }

        setupNavigationView()
        observerUIEvents()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupNavigationView() {
        binding.navView.setNavigationItemSelectedListener {
            it.isCheckable = true
            binding.drawerLayout.closeDrawers()

            true
        }
    }

    private fun observerUIEvents() {
        mainViewModel.uiEventLiveData.observe(this, Observer {
            when(it) {
                OPEN_SETTINGS_SCREEN -> openSettings()
                OPEN_FEEDBACK_SCREEN -> openFeedback()
            }
        })
    }

    private fun openSettings() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun openFeedback() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}