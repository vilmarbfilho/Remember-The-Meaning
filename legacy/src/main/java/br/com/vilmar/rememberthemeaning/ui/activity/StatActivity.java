package br.com.vilmar.rememberthemeaning.ui.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vilmar.rememberthemeaning.app.R;

import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao;
import br.com.vilmar.rememberthemeaning.ui.adapter.StatAdapter;

/**
 * Created by vilmar on 03/04/15.
 */
public class StatActivity extends BaseActivity {

    private RecyclerView mRecyclerViewStat;
    private StatAdapter mStatAdapter;

    private VocabularyDao vocabularyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_activity);

        vocabularyDao = new VocabularyDao(this);

        initView();
    }

    private void initView(){
        mRecyclerViewStat = (RecyclerView) findViewById(R.id.stat_recycler_view);
        mRecyclerViewStat.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewStat.setItemAnimator(new DefaultItemAnimator());

        mStatAdapter = new StatAdapter(this, vocabularyDao.getAll());

        mRecyclerViewStat.setAdapter(mStatAdapter);

    }
}
