package br.com.vilmar.rememberthemeaning.ui.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.vilmar.rememberthemeaning.app.R;

import java.util.ArrayList;
import java.util.List;

import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao;
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary;
import br.com.vilmar.rememberthemeaning.ui.cadastreedit.CadastreEditActivity;
import br.com.vilmar.rememberthemeaning.ui.activity.HomeActivity;
import br.com.vilmar.rememberthemeaning.ui.adapter.VocabularyAdapter;

/**
 * Created by vilmar on 22/06/14.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener,  SearchView.OnQueryTextListener {

    public static final String WORDBUNDLE = "WORDBUNDLE";

    private ListView listWords;
    private VocabularyAdapter vocabularyAdapter;
    private VocabularyDao vocabularyDao;
    private List<Vocabulary> vocabularyList;
    private FloatingActionButton fab;

    private SearchView searchView;
    private boolean flagOnSearch = false;
    private String lastWordSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        handleIntent(getActivity().getIntent());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vocabularyDao = new VocabularyDao(getActivity());
        vocabularyList = new ArrayList<Vocabulary>();

        initViews(view);
    }

    private void initViews(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        vocabularyAdapter = new VocabularyAdapter(getActivity(), R.layout.word_list_item, vocabularyList);

        listWords = (ListView) view.findViewById(R.id.list_indexed_words);
        fab.attachToListView(listWords);
        fab.setOnClickListener(this);

        listWords.setOnItemClickListener(this);
        //listWords.setEmptyView(view.findViewById(R.id.list_empty));
        //listWords.setAdapter(vocabularyAdapter);

        AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(vocabularyAdapter);
        animationAdapter.setAbsListView(listWords);

        listWords.setAdapter(animationAdapter);
    }

    private void loadWord(){
        if(vocabularyList.isEmpty()){
            //wordList.addAll(wordDao.getAll());
            vocabularyList.addAll(vocabularyDao.getAllByOrder());
        } else {
            vocabularyList.clear();
            //wordList.addAll(wordDao.getAll());
            vocabularyList.addAll(vocabularyDao.getAllByOrder());
        }
        vocabularyAdapter.notifyDataSetChanged();
    }

    private void loadWordSearch(String word){
        if(!vocabularyList.isEmpty())
            vocabularyList.clear();

        vocabularyList.addAll(vocabularyDao.search(word));
        vocabularyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        handleIntent(getActivity().getIntent());
        if(flagOnSearch){
            loadWordSearch(lastWordSearch);
        } else {
            loadWord();
        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if(searchView != null){
                searchView.setQuery(query, false);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        TextView textView = (TextView) searchView.findViewById(R.id.search_src_text);
        textView.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        flagOnSearch = true;
        lastWordSearch = s;

        if (s.length() > 140) {
            searchView.setQuery(s.substring(0, 140), false);
            return false;
        } else {
            if (s.trim().length() > 0) {
                loadWordSearch(s);
                return true;
            } else {
                loadWord();
                return true;
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                //setFragmentContent(new CadastreFragment(), R.id.content_frame, "new_cadastre", true);
                ((HomeActivity)getActivity()).selectItem(1);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Bundle bundle = new Bundle();
        //Word wordtItem = wordAdapter.getItem(position);
        Vocabulary wordtItem = vocabularyList.get(position);
        bundle.putSerializable(WORDBUNDLE, wordtItem);

        Intent intent = new Intent(getActivity(), CadastreEditActivity.class);

        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
