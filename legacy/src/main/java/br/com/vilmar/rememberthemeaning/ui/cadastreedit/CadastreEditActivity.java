package br.com.vilmar.rememberthemeaning.ui.cadastreedit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Callback;
import com.vilmar.rememberthemeaning.app.R;

import java.io.File;
import java.util.List;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.data.database.dao.LanguageDao;
import br.com.vilmar.rememberthemeaning.data.database.dao.MediaDao;
import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao;
import br.com.vilmar.rememberthemeaning.data.database.model.Language;
import br.com.vilmar.rememberthemeaning.data.database.model.Media;
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary;
import br.com.vilmar.rememberthemeaning.ui.activity.BaseActivity;
import br.com.vilmar.rememberthemeaning.util.Image;
import br.com.vilmar.rememberthemeaning.util.ImageHelper;
import br.com.vilmar.rememberthemeaning.notification.WordNotification;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogLanguage;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogLanguageChoose;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogInfo;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogPicture;
import br.com.vilmar.rememberthemeaning.ui.fragment.MainFragment;
import br.com.vilmar.rememberthemeaning.ui.widget.HourPicker;
import br.com.vilmar.rememberthemeaning.util.HelperUtil;
import br.com.vilmar.rememberthemeaning.util.IoHelper;

/**
 * Created by vilmar on 27/06/14.
 */
public class CadastreEditActivity extends BaseActivity implements View.OnClickListener, Callback{

    private EditText etWord;
    private EditText etMeaning;
    private EditText etLanguage;
    private EditText etIntervalNotification;

    //private Button btnSave;
    private LinearLayout llBtnSave;

    //private Button btnDelete;
    private LinearLayout llBtnDelete;

    private Vocabulary vocabulary;
    private VocabularyDao vocabularyDao;
    private LanguageDao languageDao;
    private WordNotification wordNotification;
    private ImageButton btnNewLanguage;

    private Media mediaImage;
    private boolean flagRemoveImage = false;

    private List<Language> listLanguage;

    private Language chosenLanguage;

    private MediaDao mediaDao;

    private RelativeLayout containerAudio;
    private RelativeLayout containerImage;

    private ImageButton btnPhoto;
    private ImageButton btnDeleteImage;

    private ImageView ivImage;
    private String path;

    //private Image photo;
    private ImageHelper imageHelper;
    private String nameImage;

    //private ImageButton btnAudio;

    private LinearLayout containerCheckbox;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(HelperUtil.apiBellowHoneycomb()){
            setTheme(R.style.Theme_NumberPicker_old);
        }

        setContentView(R.layout.edit_cadastre_activity);

        vocabularyDao = new VocabularyDao(this);
        mediaDao = new MediaDao(this);
        languageDao = new LanguageDao(this);
        wordNotification = new WordNotification(this);
        listLanguage = languageDao.getLanguage();
        //photo = new Image(getBaseContext(), this);
        imageHelper = new ImageHelper(this, this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        vocabulary = (Vocabulary) bundle.getSerializable(MainFragment.WORDBUNDLE);

        vocabularyDao.assignEmptyForeignCollection(vocabulary, "mediaList");

        setActionBar();

        initViews();
        fillDetails();

    }

    private void initViews() {
        etWord = (EditText) findViewById(R.id.et_word);
        etMeaning = (EditText) findViewById(R.id.et_meaning);
        etLanguage = (EditText) findViewById(R.id.et_language);
        etIntervalNotification = (EditText) findViewById(R.id.et_notification);
        ivImage = (ImageView) findViewById(R.id.iv_photo);

        //btnSave = (Button) findViewById(R.id.btn_save);
        llBtnSave = (LinearLayout) findViewById(R.id.ll_btn_save);

        //btnDelete = (Button) findViewById(R.id.btn_delete);
        llBtnDelete = (LinearLayout) findViewById(R.id.ll_btn_delete);

        btnPhoto = (ImageButton) findViewById(R.id.btn_photo);
        //btnAudio = (ImageButton) findViewById(R.id.btn_audio);
        btnNewLanguage = (ImageButton) findViewById(R.id.ib_new_language);
        btnDeleteImage = (ImageButton) findViewById(R.id.ib_delete_image);

        containerCheckbox = (LinearLayout) findViewById(R.id.container_checkbox);
        containerCheckbox.setVisibility(View.VISIBLE);

        containerAudio = (RelativeLayout) findViewById(R.id.container_audio);
        containerImage = (RelativeLayout) findViewById(R.id.container_image);

        etLanguage.setOnClickListener(this);
        etIntervalNotification.setOnClickListener(this);

        checkBox = (CheckBox) findViewById(R.id.checkbox);

        //btnSave.setOnClickListener(this);
        llBtnSave.setOnClickListener(this);

        //btnDelete.setOnClickListener(this);
        llBtnDelete.setOnClickListener(this);

        btnPhoto.setOnClickListener(this);
        btnDeleteImage.setOnClickListener(this);
//        btnAudio.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_DOWN:
//                        //v.setPressed(true);
//                        // Start action ...
//                        Log.i("Script", "ACTION_DOWN");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        //v.setPressed(false);
//                        Log.i("Script", "ACTION_UP");
//                        break;
//                }
//                return true;
//            }
//        });
    }

    private void fillDetails(){
        chosenLanguage = vocabulary.getLanguage();

        etWord.setText(vocabulary.getWord());
        etMeaning.setText(vocabulary.getMeaning());
        //etLanguage.setText( (word.getCategory() != 0) ? languageDao.getCategoryById(word.getCategory()).getName() : "Idioma");
        etLanguage.setText( (chosenLanguage != null) ? vocabulary.getLanguage().getName() : getString(R.string.hint_et_language));
        etIntervalNotification.setText(HelperUtil.parseMillisecondsToHour(vocabulary.getInterval()) + ":00");

        checkBox.setChecked(vocabulary.isActive());

        for(Media m : vocabulary.mediaList){
            if(IoHelper.existsFile(m.getPath()) && m.getType().equals(Media.TYPE_AUDIO)){
                //containerAudio.setVisibility(View.VISIBLE);
            } else if (IoHelper.existsFile(m.getPath()) && m.getType().equals(Media.TYPE_IMAGE)){
                mediaImage = m;
                imageHelper.loadImage(m.getPath(), ivImage, this);
            }
        }
    }

    private void setActionBar(){
        setTitle(vocabulary.getWord()); //set title in activity
        setNavigationBack(R.drawable.ic_arrow_back_white_24dp);
    }

    private void configBtnLanguage(){
        if(emptyLanguage()){
            btnNewLanguage.setBackgroundResource(android.R.color.transparent);
        } else {
            btnNewLanguage.setOnClickListener(this);
            btnNewLanguage.setBackgroundResource(R.drawable.triangle);
        }
    }

    private void showHourDialog(View view) {
        String hour =  etIntervalNotification.getText().toString();
        HourPicker hp = new HourPicker();
        hp.setListener(new HourPicker.HourPickerListener() {
            @Override
            public void OnConfirm(int hour) {
                etIntervalNotification.setText(hour + ":00");
            }

            @Override
            public void OnCancel(int hour) {
                Log.i("Script", "OnCancel");
            }
        });
        hp.setHour(HelperUtil.parseToHour(hour));
        hp.show(getSupportFragmentManager(), "hour_dialog");
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
            id.show(getSupportFragmentManager(), "info_dialog");
        } else {
            displayLanguage();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();//case R.id.btn_save:
        if (id == R.id.ll_btn_save) {
            saveWord();

            //case R.id.btn_delete:
        } else if (id == R.id.ll_btn_delete) {
            deleteWord();
        } else if (id == R.id.et_language) {
            showLanguageDialog();
        } else if (id == R.id.et_notification) {
            showHourDialog(view);
        } else if (id == R.id.btn_photo) {
            takeImage();

//            case R.id.btn_audio:
//                Log.i("Click","btn_audio");
//                break;
        } else if (id == R.id.ib_new_language) {
            cadastreLanguage();
        } else if (id == R.id.ib_delete_image) {
            deleteImage();
        }
    }

    private void displayLanguage(){
        DialogLanguageChoose dl = new DialogLanguageChoose();
        dl.setListLanguages(retrieveCategory(languageDao.getLanguage()));
        dl.setListener(new DialogLanguageChoose.OnDialogLanguageChooseListener() {
            @Override
            public void onLanguageChosen(int position) {
                chosenLanguage = listLanguage.get(position);
                etLanguage.setText(chosenLanguage.getName());
            }
        });
        dl.show(getSupportFragmentManager(), "language_dialog");
    }

    private void deleteWord(){
        DialogInfo id = new DialogInfo();
        id.setMessage(R.string.txt_dialog_delete);
        id.setPositiveButton(R.string.txt_dialog_confirm);
        id.setNegativeButton(R.string.txt_dialog_cancel);
        id.setListener(new DialogInfo.InfoDialogListener() {
            @Override
            public void onConfirm() {
                vocabularyDao.delete(vocabulary);
                wordNotification.deleteNotification(vocabulary);
                finish();
            }

            @Override
            public void onCancel() {
                Log.i("InfoDialog", "onCancel");
            }
        });
        id.show(getSupportFragmentManager(), "info_dialog");
    }

    private void deleteImage(){
        containerImage.setVisibility(View.GONE);
        //mediaImage = null;
        flagRemoveImage = true;
    }

    private void saveWord(){
        String word = etWord.getText().toString();
        String meaning = etMeaning.getText().toString();
        String interval = etIntervalNotification.getText().toString();
        boolean active = checkBox.isChecked();

        long intervalMilliseconds = HelperUtil.parseHourToMilliseconds(interval);

        vocabulary.setActive(active);
        vocabulary.setWord(word);
        vocabulary.setMeaning(meaning);
        vocabulary.setInterval(intervalMilliseconds);
        vocabulary.setLanguage(chosenLanguage);

        if (HelperUtil.validateFields(word, meaning)) {

            vocabularyDao.update(vocabulary);

            if(flagRemoveImage == true){
                mediaDao.delete(mediaImage);
                mediaImage = null;
            }

            if(mediaImage != null){
                if(mediaImage.getId() > 0){
                    mediaDao.update(mediaImage);
                } else {
                    mediaImage.setVocabulary(vocabulary);
                    mediaDao.create(mediaImage);
                }
            }

            if(vocabulary.isActive()){
                wordNotification.createNotification(vocabulary); //TODO improve it
            } else {
                wordNotification.deleteNotification(vocabulary);
            }

            Toast.makeText(this, getResources().getString(R.string.txt_update), Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_validade), Toast.LENGTH_LONG).show();
        }
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

    private void takeImage(){
        DialogPicture dp = new DialogPicture();
        dp.setListener(new DialogPicture.OnDialogPictureListener() {
            @Override
            public void getTakePhoto() {
                nameImage = imageHelper.generateIdPicture();
                imageHelper.dispatchTakePicture(nameImage);
            }

            @Override
            public void getImage() {
                imageHelper.dispatchPictureLibrary();
            }
        });
        dp.show(getSupportFragmentManager(), "picture_dialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        configBtnLanguage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int dimens[] = HelperUtil.getSizeScreen(this);
        int density = HelperUtil.getDensity(this);
        int margin = (int) getResources().getDimension(R.dimen.margin_20px);

        int width = dimens[0] - (margin * 2 * density);
        int height = 250 * density;

        if (resultCode == Activity.RESULT_OK) {
            flagRemoveImage = false;
            if(mediaImage == null){
                mediaImage = new Media();
                mediaImage.setType(Media.TYPE_IMAGE);
            }

            if (requestCode == Image.REQUEST_IMAGE_CAPTURE) {
//                String path = Constants.Store.PATH_IMG + "/" + nameImage;
//                mediaImage.setPath(path);
//                imageHelper.loadImage(path, ivImage, this);

                Uri source = Uri.fromFile(new File(Constants.Store.PATH_IMG + "/" + nameImage));
                Uri outputUri = Uri.fromFile(new File(Constants.Store.PATH_IMG, "cropped_" + nameImage));

                path = Constants.Store.PATH_IMG + "/" + "cropped_" + nameImage;

                new Crop(source).output(outputUri).withAspect(width, height).start(this);

            } else if (requestCode == Image.SELECT_FILE) {
//                String path = imageHelper.getPath(data.getData());
//                mediaImage.setPath(path);
//                imageHelper.loadImage(path, ivImage, this);
                String pathTemp = imageHelper.getPath(data.getData());
                String[] splitPath = pathTemp.split("/");
                String name = splitPath[splitPath.length-1];

                Uri source = Uri.fromFile(new File(pathTemp));
                Uri outputUri = Uri.fromFile(new File(Constants.Store.PATH_IMG, "cropped_" + name));

                path = Constants.Store.PATH_IMG + "/" + "cropped_" + name;

                new Crop(source).output(outputUri).withAspect(width, height).start(this);

            } else if(requestCode == Crop.REQUEST_CROP){
                mediaImage.setPath(path);
                imageHelper.loadImage(path, ivImage, this);
            }
        }
    }

    /**
     * CALLBACK PICASSO
     **/
    @Override
    public void onSuccess() {
        containerImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError() {
        containerImage.setVisibility(View.GONE);
        Log.e("Load Image", "Error load image");
        Toast.makeText(getBaseContext(), "Error load image", Toast.LENGTH_SHORT);
    }
    /** **/

    private void cadastreLanguage(){
        DialogLanguage dl = new DialogLanguage();
        dl.setListener(new DialogLanguage.OnDialogLanguageAddListener() {
            @Override
            public void onLanguageAdd(String language) {
                if(language.trim().isEmpty()){
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.error_language_empty), Toast.LENGTH_LONG).show();
                } else {
                    chosenLanguage = new Language(language);
                    languageDao.create(chosenLanguage);
                    etLanguage.setText(language);
                }
                configBtnLanguage();
            }

            @Override
            public void onLanguageCancel() {
                //Without implementation
            }
        });
        dl.show(getSupportFragmentManager(), "Alert Cadastre Language");
    }

    private boolean emptyLanguage(){
        boolean empty = true;
        if(languageDao.getAll().size() > 0){
            empty = false;
        }
        return empty;
    }
}
