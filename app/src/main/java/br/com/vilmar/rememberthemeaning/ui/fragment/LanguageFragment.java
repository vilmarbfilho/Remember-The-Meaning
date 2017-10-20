package br.com.vilmar.rememberthemeaning.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.vilmar.rememberthemeaning.app.R;

import java.util.ArrayList;
import java.util.List;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.database.dao.LanguageDao;
import br.com.vilmar.rememberthemeaning.database.model.Language;
import br.com.vilmar.rememberthemeaning.database.model.Vocabulary;
import br.com.vilmar.rememberthemeaning.ui.activity.CadastreEditActivity;
import br.com.vilmar.rememberthemeaning.ui.adapter.VocabularyAdapter;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogLanguage;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogLanguageChoose;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogInfo;

/**
 * Created by vilmar on 05/08/14.
 */
public class LanguageFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private LanguageDao languageDao;

    private ImageButton btnPlus;
    private ImageButton btnEdit;
    private ImageButton btnDelete;

    private VocabularyAdapter vocabularyAdapter;

    private EditText etLanguage;

    private ListView listViewWords;
    private List<Vocabulary> listVocabulary;

    private Language currentLanguage;
    private List<Language> listLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_language, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        languageDao = new LanguageDao(getActivity());

        updateListLanguage();

        if(!listLanguage.isEmpty())
            currentLanguage = listLanguage.get(0);

        initViews(view);
        configOptionsEdit();
    }

    private void initViews(View view){
        etLanguage = (EditText) view.findViewById(R.id.et_language);

        btnPlus = (ImageButton) view.findViewById(R.id.btn_plus);
        btnEdit = (ImageButton) view.findViewById(R.id.btn_edit);
        btnDelete = (ImageButton) view.findViewById(R.id.btn_delete);

        listViewWords = (ListView) view.findViewById(R.id.list_search_words);

        if(currentLanguage == null){
            etLanguage.setText(getResources().getString(R.string.txt_btn_language));
            listVocabulary = new ArrayList<Vocabulary>();
        } else {
            listVocabulary = new ArrayList<Vocabulary>(currentLanguage.getVocabularyList());
        }

        etLanguage.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        listViewWords.setOnItemClickListener(this);

        vocabularyAdapter = new VocabularyAdapter(getActivity(), R.layout.word_list_item, listVocabulary);

        listViewWords.setEmptyView(view.findViewById(R.id.empty_view));
        //listViewWords.setAdapter(vocabularyAdapter);

        AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(vocabularyAdapter);
        animationAdapter.setAbsListView(listViewWords);

        listViewWords.setAdapter(animationAdapter);

    }

    private void updateListLanguage(){
        listLanguage = languageDao.getAllByOrder();
    }

    private void configOptionsEdit(){
        if(listLanguage != null && listLanguage.size() > 0){
            btnEdit.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            etLanguage.setText(currentLanguage.getName());
        } else {
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
            etLanguage.setText(getResources().getString(R.string.txt_btn_language));
        }
    }

    private void updateListWord(){
        listVocabulary.clear();

        if(currentLanguage != null && currentLanguage.getVocabularyList() != null)
            listVocabulary.addAll(currentLanguage.getVocabularyList());

        vocabularyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listVocabulary.size() > 0){
            updateListWord();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_language:
                showLanguageDialog();
                break;

            case R.id.btn_plus:
                cadastreLanguage();
                break;

            case R.id.btn_edit:
                editLanguage();
                break;

            case R.id.btn_delete:
                deleteLanguage();
                break;
        }
    }

    private void showLanguageDialog(){
        if(emptyLanguage()){
            DialogInfo id = new DialogInfo();
            id.setMessage(R.string.txt_dialog_language);
            id.setPositiveButton(R.string.txt_dialog_confirm);
            id.setNegativeButton(R.string.txt_dialog_cancel);
            id.setListener(new DialogInfo.InfoDialogListener() {
                @Override
                public void onConfirm() {
                    cadastreLanguage();
                }

                @Override
                public void onCancel() {
                    Log.i("InfoDialog", "onCancel");
                }
            });
            id.show(getFragmentManager(), "info_dialog");
        } else {
            displayLanguage();
        }
    }

    private void displayLanguage(){
        DialogLanguageChoose dl = new DialogLanguageChoose();
        dl.setListLanguages(retrieveCategory(languageDao.getLanguage()));
        dl.setListener(new DialogLanguageChoose.OnDialogLanguageChooseListener() {
            @Override
            public void onLanguageChosen(int position) {
                currentLanguage = listLanguage.get(position);
                etLanguage.setText(currentLanguage.getName());
                updateListWord();
            }
        });
        dl.show(getFragmentManager(), "language_dialog");
    }

    private String[] retrieveCategory(List<Language> language){
        String[] array = new String[language.size()];
        int index = 0;
        for(Language value : language){
            array[index] = value.getName();
            index++;
        }
        return array;
    }

    private void cadastreLanguage(){
        DialogLanguage dl = new DialogLanguage();
        dl.setTitle(R.string.title_new_language);
        dl.setListener(new DialogLanguage.OnDialogLanguageAddListener() {
            @Override
            public void onLanguageAdd(String language) {
                if(language.trim().isEmpty()){
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_language_empty), Toast.LENGTH_LONG).show();
                } else {
                    currentLanguage = new Language(language);
                    languageDao.create(currentLanguage);
                    etLanguage.setText(language);
                    updateListLanguage();
                    configOptionsEdit();
                    updateListWord();
                }
            }

            @Override
            public void onLanguageCancel() {
                //Without implementation
            }
        });
        dl.show(getFragmentManager(), "Alert Cadastre Language");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        Vocabulary wordtItem = vocabularyAdapter.getItem(position);
        bundle.putSerializable(Vocabulary.WORDBUNDLE, wordtItem);

        Intent intent;
        intent = new Intent(getActivity(), CadastreEditActivity.class);

        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    private void deleteLanguage(){
        if(currentLanguage != null &&  currentLanguage.getVocabularyList() != null && currentLanguage.getVocabularyList().size() > 0){
            DialogInfo id = new DialogInfo();
            id.setMessage(R.string.txt_dialog_language_delete);
            id.setPositiveButton(R.string.txt_dialog_confirm);
            id.show(getFragmentManager(), "info_warning_delete");
        } else {
            if(currentLanguage != null){
                DialogInfo id = new DialogInfo();
                id.setMessage(R.string.txt_dialog_confirm_delete_language);
                id.setPositiveButton(R.string.txt_dialog_confirm);
                id.setNegativeButton(R.string.txt_dialog_cancel);
                id.setListener(new DialogInfo.InfoDialogListener() {
                    @Override
                    public void onConfirm() {
                        confirmDeleteLanguage();
                    }

                    @Override
                    public void onCancel() {
                        Log.i("InfoDialog", "onCancel");
                    }
                });
                id.show(getFragmentManager(), "info_delete");
            }
        }
    }

    private void confirmDeleteLanguage() {
        languageDao.delete(currentLanguage);

        listLanguage = languageDao.getAllByOrder();

        if(listLanguage.size() > 0){
            currentLanguage = listLanguage.get(0);
        } else {
            currentLanguage = null;
        }

        configOptionsEdit();
        updateListWord();
    }

    private void editLanguage(){
        DialogLanguage dl = new DialogLanguage();
        dl.setTitle(R.string.title_edit_language);
        dl.setValue(etLanguage.getText().toString());
        dl.setListener(new DialogLanguage.OnDialogLanguageAddListener() {
            @Override
            public void onLanguageAdd(String language) {
                if(language.trim().isEmpty()){
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_language_empty), Toast.LENGTH_LONG).show();
                } else {
                    currentLanguage.setName(language);
                    languageDao.update(currentLanguage);
                    etLanguage.setText(language);

                    updateListWord();
                }
            }

            @Override
            public void onLanguageCancel() {
                //Without implementation
            }
        });
        dl.show(getFragmentManager(), "Alert Edit Language");
    }

    private boolean emptyLanguage(){
        boolean empty = true;
        if(listLanguage.size() > 0){
            empty = false;
        }
        return empty;
    }
}
