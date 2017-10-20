package br.com.vilmar.rememberthemeaning;

import android.os.Environment;

/**
 * Created by vilmar on 27/06/14.
 */
public class Constants {

    public static final String PATH_STORAGE = Environment.getExternalStorageDirectory().getPath();

    public static final boolean APP_HOMOLOGACAO = false;

    public static class URL {
        public static final String HELP = "http://vlabs.com.br/android/rtm/help.html";
    }

    public static final class Database {
        public static final int DATABASE_VERSION = 1;
    }

    public static final class Settings{
        //public static final int HOUR_DEFAULT = 24;
        public static final int MILLISECONDS_DEFAULT = 86400000; //24 hours
    }

    public static class SharedPreferencesKeys {
        public static final String SHAREDPREF = "SHAREDPREF";
        public static final String SETTINGS = "SETTINGS";
        public static final String COUNTNOTIFICATION = "COUNTNOTIFICATION";
        public static final String ARRAYREMINDERS = "ARRAYREMINDERS";
        public static final String CYCLE_NOTIFICATION = "CYCLENOTIFICATION";
    }

    public static class Store{
        public static final String PATH_AUDIO = PATH_STORAGE  + "/rtm/record";
        public static final String PATH_IMG = PATH_STORAGE  + "/rtm/image";
    }
}
