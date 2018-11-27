package br.com.vilmar.rememberthemeaning.ui.common

import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun fragmentName() : String

}