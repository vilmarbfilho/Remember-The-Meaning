package br.com.vilmar.rememberthemeaning.data.database.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import br.com.vilmar.rememberthemeaning.data.database.DBHelper;
import br.com.vilmar.rememberthemeaning.data.database.model.Media;

/**
 * Created by vilmar on 3/2/15.
 */
public class MediaDao {

    private static Dao<Media, Integer> mediaDAO = null;
    private QueryBuilder<Media, Integer> queryBuilder = null;

    public MediaDao(Context context) {
        try {
            mediaDAO = DBHelper.getHelper(context).getDao(Media.class);
        } catch (SQLException e) {
            Log.e("MediaDao", e.getMessage());
        }
    }

    public void create(Media media) {
        try {
            mediaDAO.create(media);
        } catch (SQLException e) {
            Log.e("DAO create", e.getMessage());
        }
    }

    public void update(Media media) {
        try {
            mediaDAO.update(media);
        } catch (SQLException e) {
            Log.e("DAO update", e.getMessage());
        }
    }

    public void delete(Media media) {
        try {
            mediaDAO.delete(media);
        } catch (SQLException e) {
            Log.e("DAO delete", e.getMessage());
        }
    }

    public Media getMediaById(int id) {
        try {
            return mediaDAO.queryForId(id);
        } catch (SQLException e) {
            Log.e("DAO getWordById", e.getMessage());
        }
        return null;
    }

    public List<Media> getAll() {
        try {
            return mediaDAO.queryForAll();
        } catch (SQLException e) {
            Log.e("DAO delete", e.getMessage());
        }
        return null;
    }
}
