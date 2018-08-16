package br.com.vilmar.rememberthemeaning.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.vilmar.rememberthemeaning.app.R;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.ui.fragment.CadastreFragment;
import br.com.vilmar.rememberthemeaning.ui.fragment.LanguageFragment;
import br.com.vilmar.rememberthemeaning.ui.fragment.MainFragment;
import br.com.vilmar.rememberthemeaning.ui.fragment.SettingsFragment;
import br.com.vilmar.rememberthemeaning.util.HelperUtil;
import io.fabric.sdk.android.Fabric;


/**
 * Created by vilmar on 22/06/14.
 */
public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private String[] textOptions;
    private Toolbar toolbar;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        if(HelperUtil.apiBellowHoneycomb()){
            setTheme(R.style.Theme_NumberPicker_old);
        }

        setContentView(R.layout.home_activity);

        setTitle("Menu");

        initViews();
        onConfigListener();
        onConfigListItem();

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerToggle = new ActionBarDrawerToggle(this,  drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        textOptions = getResources().getStringArray(R.array.itens_menu_string);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    private void onConfigListener() {
        drawerList.setOnItemClickListener(this);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void onConfigListItem() {
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, textOptions));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(currentFragment instanceof CadastreFragment){
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        selectItem(position);
    }

    public void selectItem(int position) {

        switch (position) {
            case 0:
                MainFragment mainFragment = new MainFragment();
                currentFragment = mainFragment;
                loadFragment(mainFragment);
                break;
            case 1:
                CadastreFragment cadastreFragment = new CadastreFragment();
                currentFragment = cadastreFragment;
                setFragmentContent(cadastreFragment, R.id.content_frame, "new_cadastre", true);
                break;
            case 2:
                LanguageFragment languageFragment = new LanguageFragment();
                currentFragment = languageFragment;
                setFragmentContent(languageFragment, R.id.content_frame, "category", true);
                break;
            case 3:
                SettingsFragment settingsFragment = new SettingsFragment();
                currentFragment = settingsFragment;
                setFragmentContent(settingsFragment, R.id.content_frame, "settings", true);
                break;
            case 4:
                //TODO TEMPORARIO DEPOIS VOLTAR COM A OPÇÃO DE AJUDA
                //TrackerGoogle.event(this, Constants.GoogleAnalytics.CATEGORY_MENU, Constants.GoogleAnalytics.ACTION_AJUDA, null, null);
                //openActivity(this, WebViewActivity.class);
                sendEmail();
                break;
//            case 5:
//                openActivity(getBaseContext(), StatActivity.class);
//                break;
        }
        drawerLayout.closeDrawer(drawerList);
    }

    private void sendEmail(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"vlabs.bh@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "feedback");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Escolha um cliente de email:"));
    }

}
