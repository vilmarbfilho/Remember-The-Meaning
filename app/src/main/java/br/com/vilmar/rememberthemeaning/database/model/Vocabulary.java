package br.com.vilmar.rememberthemeaning.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by vilmar on 22/06/14.
 */
@DatabaseTable(tableName = "vocabulary")
public class Vocabulary implements Serializable {

    public static final String LANGUAGE_ID_FIELD_NAME = "language_id";
    public static final String WORDBUNDLE = "WORDBUNDLE";

    @DatabaseField(generatedId = true, unique = true, canBeNull = false)
    private int id;

    @DatabaseField(canBeNull = false)
    private boolean active;

    @DatabaseField(canBeNull = false)
    private String word;

    @DatabaseField(canBeNull = false)
    private String meaning;

    @DatabaseField(canBeNull = false)
    private long interval;

    @DatabaseField(canBeNull = true)
    private int stat;

    @DatabaseField
    private long notification;

    //@DatabaseField(columnName = CATEGORY_ID_FIELD_NAME)
    //private int category;

    @DatabaseField(columnName = LANGUAGE_ID_FIELD_NAME, foreign = true, canBeNull = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Language language;

    @ForeignCollectionField(eager = false)
    public Collection<Media> mediaList;

    public Vocabulary(){}

    public Vocabulary(boolean active, String word, String meaning, long interval, Language language){
        this.active = active;
        this.word = word;
        this.meaning = meaning;
        this.interval = interval;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

//    public String getIntervalString() {
//        return intervalString;
//    }

//    public void setIntervalString(String intervalString) {
//        this.intervalString = intervalString;
//    }

    public long getNotification() {
        return notification;
    }

    public void setNotification(long notification) {
        this.notification = notification;
    }

//    public int getCategory() {
//        return category;
//    }
//
//    public void setCategory(int category) {
//        this.category = category;
//    }


    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public Collection<Media> getMediaList() {
        return mediaList;
    }

}
