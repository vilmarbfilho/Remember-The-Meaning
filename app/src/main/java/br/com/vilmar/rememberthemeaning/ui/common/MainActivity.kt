package br.com.vilmar.rememberthemeaning.ui.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import br.com.vilmar.rememberthemeaning.ui.NavigationHost
import br.com.vilmar.rememberthemeaning.ui.deck.DeckFragment
import com.vilmar.rememberthemeaning.app.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationHost, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        //AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            navigateTo(DeckFragment.newInstance(), false)
        }
    }

    override fun navigateTo(fragment: BaseFragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(fragment.fragmentName())
        }

        transaction.commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}