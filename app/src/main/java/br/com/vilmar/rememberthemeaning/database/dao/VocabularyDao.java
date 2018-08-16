package br.com.vilmar.rememberthemeaning.database.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import br.com.vilmar.rememberthemeaning.database.DBHelper;
import br.com.vilmar.rememberthemeaning.database.model.Media;
import br.com.vilmar.rememberthemeaning.database.model.Vocabulary;

/**
 * Created by vilmar on 22/06/14.
 */
public class VocabularyDao {

    private static Dao<Vocabulary, Integer> vocabularyDao = null;
    private QueryBuilder<Vocabulary, Integer> queryBuilder = null;

    private Dao<Media, Integer> mediaDao = null;

    public VocabularyDao(Context context) {
        try {
            vocabularyDao = DBHelper.getHelper(context).getDao(Vocabulary.class);
            mediaDao = DBHelper.getHelper(context).getDao(Media.class);
        } catch (SQLException e) {
            Log.e("WordDao", e.getMessage());
        }
    }

    public void create(Vocabulary vocabulary) {
        try {
            vocabularyDao.create(vocabulary);
        } catch (SQLException e) {
            Log.e("DAO create", e.getMessage());
        }
    }

    public void update(Vocabulary vocabulary) {
        try {
            vocabularyDao.update(vocabulary);
        } catch (SQLException e) {
            Log.e("DAO update", e.getMessage());
        }
    }

    public void delete(Vocabulary vocabulary) {

        try{
            DeleteBuilder db = mediaDao.deleteBuilder();
            db.where().eq("vocabulary_id", vocabulary.getId());
            mediaDao.delete(db.prepare());
            vocabularyDao.delete(vocabulary);
        } catch (SQLException e) {
            Log.e("DAO delete", e.getMessage());
        }

//        try {
//            vocabularyDao.delete(vocabulary);
//        } catch (SQLException e) {
//            Log.e("DAO delete", e.getMessage());
//        }
    }

    public Vocabulary getWordById(int id) {
        try {
            return vocabularyDao.queryForId(id);
        } catch (SQLException e) {
            Log.e("DAO getWordById", e.getMessage());
        }
        return null;
    }

    public List<Vocabulary> getAll() {
        try {
            return vocabularyDao.queryForAll();
        } catch (SQLException e) {
            Log.e("DAO delete", e.getMessage());
        }
        return null;
    }

//    public List<Vocabulary> getLastWords() {
//        //SELECT * FROM words ORDER BY id DESC limit 10
//        queryBuilder = wordDAO.queryBuilder();
//        //queryBuilder.orderBy("id", false).limit(10);
//        queryBuilder.orderBy("id", false);
//        try {
//            PreparedQuery<Vocabulary> preparedQuery = queryBuilder.prepare();
//            return wordDAO.query(preparedQuery);
//        } catch (SQLException e) {
//            Log.e("DAO getLastWords", e.getMessage());
//        }
//        return null;
//    }

    public List<Vocabulary> getWords() {
        //SELECT * FROM words ORDER BY source  COLLATE NOCASE ASC
        queryBuilder = vocabularyDao.queryBuilder();
        //queryBuilder.orderBy("source", true).queryRaw("Name COLLATE NOCASE");
        try {
            return vocabularyDao.queryBuilder().orderByRaw("source COLLATE NOCASE").query();
        } catch (SQLException e) {
            Log.e("DAO getWords", e.getMessage());
        }
        return null;
    }

    public List<Vocabulary> getAllByOrder(){
        queryBuilder = vocabularyDao.queryBuilder();
        List<Vocabulary> listLanguage;
        try {
            queryBuilder.orderByRaw("word COLLATE NOCASE").query();
            PreparedQuery<Vocabulary> preparedQuery = queryBuilder.prepare();

            listLanguage = vocabularyDao.query(preparedQuery);
            if(listLanguage != null)
                return listLanguage;

        } catch (SQLException e) {
            Log.e("WordDAO", e.getMessage());
        }

        return null;
    }

//    public List<Vocabulary> getWordsByLanguage(int idCategory){
//        //SELECT * FROM words WHERE category_id = # ORDER BY source  COLLATE NOCASE ASC
//        queryBuilder = wordDAO.queryBuilder();
//        try {
//            queryBuilder.orderByRaw("source COLLATE NOCASE").where().eq(Vocabulary.CATEGORY_ID_FIELD_NAME, idCategory); //fix
//            PreparedQuery<Vocabulary> preparedQuery = queryBuilder.prepare();
//            return wordDAO.query(preparedQuery);
//        } catch (SQLException e) {
//            Log.e("DAO getWordsByLanguage", e.getMessage());
//        }
//        return null;
//    }

    public List<Vocabulary> searchWords(String word) {
        //SELECT * FROM words WHERE source LIKE '%word%' OR result LIKE '%word%'
        queryBuilder = vocabularyDao.queryBuilder();
        try {
            queryBuilder.where().like("meaning", "%" + word + "%").or().like("word", "%" + word + "%"); //TODO Pesquisar se existe outra maneira de colocar %
            PreparedQuery<Vocabulary> preparedQuery = queryBuilder.prepare();
            return vocabularyDao.query(preparedQuery);
        } catch (SQLException e) {
            Log.e("DAO searchWords", e.getMessage());
        }
        return null;
    }

//    public int countWordsByCategory(int idCategory){
//        //SELECT count(*) FROM words WHERE category_id = #
//        if(idCategory == 0)
//            return 0;
//
//        try {
//            int numRows = (int) wordDAO.queryBuilder().where().eq("category_id", idCategory).countOf();
//            return numRows;
//        } catch (SQLException e) {
//            Log.e("DAOcountWordsByCategory", "CountWordsByCategory: " + e.getMessage());
//        }
//        return 0;
//    }

    public ForeignCollection getEmptyForeignCollection(String collection){
        try{
            return vocabularyDao.getEmptyForeignCollection(collection);
        } catch (SQLException e) {
            Log.e("Vocabulary DAO", e.getMessage());
        }
        return null;
    }

    public void assignEmptyForeignCollection(Vocabulary vocabulary, String fieldName){
        try{
            vocabularyDao.assignEmptyForeignCollection(vocabulary, fieldName);
        } catch (SQLException e) {
            Log.e("Vocabulary DAO", e.getMessage());
        }
    }

}
