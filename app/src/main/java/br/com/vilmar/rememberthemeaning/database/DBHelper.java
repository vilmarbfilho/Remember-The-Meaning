package br.com.vilmar.rememberthemeaning.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.database.model.Language;
import br.com.vilmar.rememberthemeaning.database.model.Media;
import br.com.vilmar.rememberthemeaning.database.model.Vocabulary;

/**
 * Created by vilmar on 22/06/14.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "db_word.db";

    private static final int DATABASE_VERSION = Constants.Database.DATABASE_VERSION;

    private static DBHelper helper = null;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getHelper(Context context) {
        if (helper == null) {
            helper = new DBHelper(context);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Vocabulary.class);
            TableUtils.createTable(connectionSource, Language.class);

            TableUtils.createTable(connectionSource, Media.class);

            Log.i("ORMLITE", "createTable");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            //************** UPDATE DB ******************
            if(newVersion == 2){
                updateVersion2(db);
            } else if(newVersion == 3){
                updateVersion3(db);
            } else {
                TableUtils.dropTable(connectionSource, Vocabulary.class, true);
                TableUtils.dropTable(connectionSource, Language.class, true);
                TableUtils.dropTable(connectionSource, Media.class, true);
            }
            //************** UPDATE DB ******************

            //onCreate(db, connectionSource);
            Log.i("ORMLITE", "onUpgrade");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateVersion2(SQLiteDatabase db) throws SQLException{
        TableUtils.createTable(connectionSource, Language.class);
        db.execSQL("ALTER TABLE `words` ADD COLUMN `category_id` INTEGER REFERENCES `category` (`id`)");
    }

    private void updateVersion3(SQLiteDatabase db) throws SQLException{
        TableUtils.createTable(connectionSource, Media.class);
        TableUtils.createTable(connectionSource, Vocabulary.class);
        TableUtils.createTable(connectionSource, Language.class);

        db.execSQL("INSERT INTO `vocabulary` (`word`, `meaning`, `active`, `interval`, `notification`, `language_id`, `id`) SELECT `source`, `result`, `active`, `interval`, `notification`, `category_id`, `id` FROM `words`");
        db.execSQL("DROP TABLE `words`");

        db.execSQL("INSERT INTO `language` (`name`, `id`) SELECT `name`, `id` FROM `category`");
        db.execSQL("DROP TABLE `category`");
    }

    @Override
    public void close() {
        super.close();
    }
}
