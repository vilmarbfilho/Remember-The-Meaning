package br.com.vilmar.rememberthemeaning.data.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by vilmar on 25/07/14.
 */
@DatabaseTable(tableName = "language")
public class Language implements Serializable {

    public static final String ID_FIELD_NAME = "id";

    @DatabaseField(generatedId = true, unique = true, canBeNull = false)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @ForeignCollectionField(eager = false)
    public Collection<Vocabulary> vocabularyList;

    public Language(){};

    public Language(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Vocabulary> getVocabularyList() {
        return vocabularyList;
    }
}
