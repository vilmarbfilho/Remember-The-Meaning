package br.com.vilmar.rememberthemeaning.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.vilmar.rememberthemeaning.app.R;

import java.util.ArrayList;
import java.util.List;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.database.dao.LanguageDao;
import br.com.vilmar.rememberthemeaning.database.dao.VocabularyDao;
import br.com.vilmar.rememberthemeaning.database.model.Vocabulary;
import br.com.vilmar.rememberthemeaning.ui.adapter.RememberAdapter;
import br.com.vilmar.rememberthemeaning.util.TinyDB;

/**
 * Created by vilmar on 2/12/15.
 */
public class RememberActivity extends BaseActivity {

    private TinyDB tinydb;
    private VocabularyDao vocabularyDao;
    private LanguageDao languageDao;
    private List<Integer> reminders;

    private RecyclerView recList;
    private RememberAdapter rememberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remember_activity);

        //TrackerGoogle.event(this, Constants.GoogleAnalytics.CATEGORY_SCREEN, Constants.GoogleAnalytics.ACTION_ENTER_SCREEN, Constants.GoogleAnalytics.LABEL_SCREEN_REMEMBER, null);

        tinydb = new TinyDB(this);
        vocabularyDao = new VocabularyDao(this);
        languageDao = new LanguageDao(this);
        reminders = tinydb.getListInt(Constants.SharedPreferencesKeys.ARRAYREMINDERS, this);
        tinydb.remove(Constants.SharedPreferencesKeys.ARRAYREMINDERS);

        setActionBar();
        initViews();

        clearCountReminders();
    }

    private void setActionBar(){
        setTitle(getResources().getString(R.string.btn_back_exit));
        setNavigationBack(R.drawable.ic_arrow_back_white_24dp);
    }

    private void initViews(){
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setLayoutManager(new LinearLayoutManager(this));
        recList.setItemAnimator(new DefaultItemAnimator());

        rememberAdapter = new RememberAdapter(this, getRemenders());
        rememberAdapter.setOnItemClickListener(new RememberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("Script", "pos: " + position);
                rememberAdapter.remove(position);
            }
        });

        recList.setAdapter(rememberAdapter);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Vocabulary> getRemenders(){ //TODO improve it
        List<Vocabulary> listVocabulary = new ArrayList<Vocabulary>();

        for(int i = 0; i < reminders.size(); i++){
            Vocabulary vocabulary = vocabularyDao.getWordById(reminders.get(i));
            processStatWord(vocabulary);
            listVocabulary.add(vocabulary);
        }

        return listVocabulary;
    }

    private void processStatWord(Vocabulary mVocabulary){
        int stat = mVocabulary.getStat() + 1;
        mVocabulary.setStat(stat);
        vocabularyDao.update(mVocabulary);
    }
}
