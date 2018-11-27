package br.com.vilmar.rememberthemeaning.ui

import br.com.vilmar.rememberthemeaning.ui.common.BaseFragment

interface NavigationHost {

    fun navigateTo(fragment: BaseFragment, addToBackstack: Boolean)

}