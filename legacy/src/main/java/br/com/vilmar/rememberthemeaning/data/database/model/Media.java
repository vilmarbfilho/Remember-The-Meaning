package br.com.vilmar.rememberthemeaning.data.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by vilmar on 2/25/15.
 */
@DatabaseTable(tableName = "media")
public class Media implements Serializable{

    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_IMAGE = "image";

    @DatabaseField(generatedId = true, unique = true, canBeNull = false)
    private int id;

    @DatabaseField(canBeNull = false)
    private String type;

    @DatabaseField(canBeNull = false)
    private String path;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true, foreignAutoRefresh = true)
    public Vocabulary vocabulary;

    public Media(){}

    public Media(Vocabulary vocabulary, String type, String path){
        this.vocabulary = vocabulary;
        this.type = type;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }
}
