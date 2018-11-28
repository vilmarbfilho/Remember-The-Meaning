package br.com.vilmar.rememberthemeaning.ui.common

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

abstract class BaseFragment : Fragment() {

    fun setupActionBar(toolbar : Toolbar) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    fun setupTitleActionBar(title : String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    abstract fun fragmentName() : String

}