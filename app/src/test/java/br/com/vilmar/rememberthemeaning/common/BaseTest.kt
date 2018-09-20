package br.com.vilmar.rememberthemeaning.common

import br.com.vilmar.rememberthemeaning.data.database.model.Language
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary

open class BaseTest {

    fun createVocabularyList(): List<Vocabulary> {
        val list = mutableListOf<Vocabulary>()

        val language = Language("EN")

        list.add(Vocabulary(true, "Word", "Meaning", 1000L, language))
        list.add(Vocabulary(true, "Girl", "Menina", 1000L, language))
        list.add(Vocabulary(true, "Boy", "Menino", 1000L, language))
        list.add(Vocabulary(true, "Boat", "Barco", 1000L, language))
        list.add(Vocabulary(true, "House", "Casa", 1000L, language))

        return list
    }

}