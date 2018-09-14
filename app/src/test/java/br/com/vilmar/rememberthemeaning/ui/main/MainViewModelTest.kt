package br.com.vilmar.rememberthemeaning.ui.main

import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit


class MainViewModelTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @InjectMocks
    lateinit var mainViewModel: MainViewModel

    @Test
    fun `on start set vocabulary list`() {

    }
}