package br.com.vilmar.rememberthemeaning.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vilmar.rememberthemeaning.app.R;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.util.SharedPreferenceHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by vilmar on 22/06/14.
 */
public class BaseActivity extends AppCompatActivity {

    protected Fragment currentFragment;
    private ActionBar actionBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean setActionbar() {
        if (toolbar != null) {
            return true;
        } else {
            if (findViewById(R.id.toolbar) != null) {
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
                setSupportActionBar(toolbar);
                return true;
            } else {
                return false;
            }
        }
    }

    public void setTitle(String title) {
        if (setActionbar()) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void setNavigationBack(int drawable) {
        if (setActionbar())
            toolbar.setNavigationIcon(drawable);
    }

    public void setFragmentContent(Fragment fragment, int resourceID, String label, boolean toBack) {

        if (currentFragment != fragment) {

            currentFragment = fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

            if (toBack) {
                transaction.addToBackStack(label);
            }

            transaction.replace(resourceID, fragment).commitAllowingStateLoss();
        }
    }

    public void loadFragment(Fragment fragment) {
        currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
    }

    public void openActivity(Context context, Class activityClass) {
        Intent intent = new Intent(context, activityClass);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public Typeface customFont() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Cuprum-Bold.ttf");
        return custom_font;
    }

    //@Override
    //protected void attachBaseContext(Context newBase) {
    //    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    //}

    public void clearCountReminders() {
        SharedPreferenceHelper.write(this, Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.COUNTNOTIFICATION, 0);
    }

}
