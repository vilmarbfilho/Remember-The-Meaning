package br.com.vilmar.rememberthemeaning.ui.common

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.vilmar.rememberthemeaning.app.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import android.support.v4.view.GravityCompat
import br.com.vilmar.rememberthemeaning.ui.activity.HomeActivity
import com.vilmar.rememberthemeaning.app.databinding.MainActivityBinding


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector : DispatchingAndroidInjector<Fragment>

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        setupNavigationView()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
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

        binding.footerItemSettings.setOnClickListener {
            openSettings()
        }

        binding.footerItemFeedback.setOnClickListener {
            openFeedback()
        }
    }

    private fun openSettings() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun openFeedback() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}