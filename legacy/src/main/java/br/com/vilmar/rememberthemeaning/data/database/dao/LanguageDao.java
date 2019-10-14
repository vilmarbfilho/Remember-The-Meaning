package br.com.vilmar.rememberthemeaning.data.database.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import br.com.vilmar.rememberthemeaning.data.database.DBHelper;
import br.com.vilmar.rememberthemeaning.data.database.model.Language;

/**
 * Created by vilmar on 01/08/14.
 */
public class LanguageDao {

    private static Dao<Language, Integer> languageDAO = null;
    private QueryBuilder<Language, Integer> queryBuilder = null;

    public LanguageDao(Context context) {
        try {
            languageDAO = DBHelper.getHelper(context).getDao(Language.class);
        } catch (SQLException e) {
            Log.e("WordDao", e.getMessage());
        }
    }

    public void create(Language language) {
        try {
            languageDAO.create(language);
        } catch (SQLException e) {
            Log.e("DAO create", e.getMessage());
        }
    }

    public void update(Language language) {
        try {
            languageDAO.update(language);
        } catch (SQLException e) {
            Log.e("DAO update", e.getMessage());
        }
    }

    public void delete(Language language) {
        try {
            languageDAO.delete(language);
        } catch (SQLException e) {
            Log.e("DAO delete", e.getMessage());
        }
    }

//    public Language getCategoryById(int id) {
//        try {
//            return categoryDAO.queryForId(id);
//        } catch (SQLException e) {
//            Log.e("DAO getWordById", e.getMessage());
//        }
//        return null;
//    }

    public List<Language> getAll() {
        try {
            return languageDAO.queryForAll();
        } catch (SQLException e) {
            Log.e("DAO delete", e.getMessage());
        }
        return null;
    }

    public List<Language> getAllByOrder(){
        queryBuilder = languageDAO.queryBuilder();
        List<Language> listLanguage;
        try {
            queryBuilder.orderByRaw("name COLLATE NOCASE").query();
            PreparedQuery<Language> preparedQuery = queryBuilder.prepare();

            listLanguage = languageDAO.query(preparedQuery);
            if(listLanguage != null)
                return listLanguage;

        } catch (SQLException e) {
            Log.e("LanguageDAO", e.getMessage());
        }

        return null;
    }

    public List<Language> getLanguage() {
        queryBuilder = languageDAO.queryBuilder();
        try {
            return languageDAO.queryBuilder().orderByRaw("name COLLATE NOCASE").query();
        } catch (SQLException e) {
            Log.e("DAO getCategory", e.getMessage());
        }
        return null;
    }

    public Language getByLanguage(String category){
        //select id from category where name = 'category'
        queryBuilder = languageDAO.queryBuilder();
        try {
            queryBuilder.where().like("name", category);
            PreparedQuery<Language> preparedQuery = queryBuilder.prepare();
            return languageDAO.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            Log.e("DAO searchWords", e.getMessage());
        }
        return null;
    }

//    public Language getFirstCategoryByOrder(){
//        //SELECT id FROM category ORDER BY name  COLLATE NOCASE ASC LIMIT 1
//        queryBuilder = categoryDAO.queryBuilder();
//        try {
//            queryBuilder.orderByRaw("name COLLATE NOCASE").limit(1).query();
//            PreparedQuery<Language> preparedQuery = queryBuilder.prepare();
//
//            Language language = categoryDAO.queryForFirst(preparedQuery);
//            if(language != null)
//                return language;
//
//        } catch (SQLException e) {
//            Log.e("LanguageDAO", e.getMessage());
//        }
//
//        return null;
//    }
}
