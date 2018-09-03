package br.com.vilmar.rememberthemeaning;

import android.app.Application;

import com.google.gson.Gson;
import com.vilmar.rememberthemeaning.app.R;

import java.io.File;

import br.com.vilmar.rememberthemeaning.data.database.model.Settings;
import br.com.vilmar.rememberthemeaning.util.IoHelper;
import br.com.vilmar.rememberthemeaning.util.SharedPreferenceHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by vilmar on 06/07/14.
 */
public class RememberTheMeaningApplication extends Application {

    private static RememberTheMeaningApplication instance;

    public static RememberTheMeaningApplication getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Configure a aplicação no AndroidManifest.xml");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (!checkSharedPrefSettings())
            createSharedPrefSettings();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Museo-Regular-300.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        createDirs();
    }

    private boolean checkSharedPrefSettings() {
        File f = new File("/data/data/" + getPackageName() + "/shared_prefs/SHAREDPREF.xml");

        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }

    private void createSharedPrefSettings() {
        Settings settingsObj = new Settings();

        settingsObj.setIntervalGlobal(Constants.Settings.MILLISECONDS_DEFAULT);

        String settingsJson = new Gson().toJson(settingsObj);

        SharedPreferenceHelper.write(this, Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.SETTINGS, settingsJson);
    }

    private void createDirs(){
//        if(!IoHelper.existsDir(Constants.Store.PATH_AUDIO)){
//            IoHelper.createDir(Constants.Store.PATH_AUDIO);
//        }

        if(!IoHelper.existsDir(Constants.Store.PATH_IMG)){
            IoHelper.createDir(Constants.Store.PATH_IMG);
        }
    }

}
