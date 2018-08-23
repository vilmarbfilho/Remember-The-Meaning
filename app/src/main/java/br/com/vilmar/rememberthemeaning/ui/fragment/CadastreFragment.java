package br.com.vilmar.rememberthemeaning.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Callback;
import com.vilmar.rememberthemeaning.app.R;

import java.io.File;
import java.util.List;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.data.database.dao.MediaDao;
import br.com.vilmar.rememberthemeaning.data.database.model.Media;
import br.com.vilmar.rememberthemeaning.feature.Image;
import br.com.vilmar.rememberthemeaning.feature.ImageHelper;
import br.com.vilmar.rememberthemeaning.feature.RecordAudio;
import br.com.vilmar.rememberthemeaning.data.database.dao.LanguageDao;
import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao;
import br.com.vilmar.rememberthemeaning.data.database.model.Language;
import br.com.vilmar.rememberthemeaning.data.database.model.Settings;
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary;
import br.com.vilmar.rememberthemeaning.notification.WordNotification;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogLanguage;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogLanguageChoose;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogInfo;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogOptionsRecord;
import br.com.vilmar.rememberthemeaning.ui.dialog.DialogPicture;
import br.com.vilmar.rememberthemeaning.ui.widget.HourPicker;
import br.com.vilmar.rememberthemeaning.util.HelperUtil;
import br.com.vilmar.rememberthemeaning.util.SharedPreferenceHelper;

/**
 * Created by vilmar on 27/06/14.
 */
public class CadastreFragment extends BaseFragment implements View.OnClickListener, Callback{

    private EditText etWord;
    private EditText etMeaning;
    private EditText etLanguage;
    private EditText etIntervalNotification;

    //private Button btnSave;
    private LinearLayout llBtnSave;

    private VocabularyDao vocabularyDao;
    private LanguageDao languageDao;
    private Settings settingsObj;
    private WordNotification wordNotification;
    private ImageButton btnNewLanguage;

    private Media mediaRecord;
    private Media mediaImage;

    private List<Language> listLanguage;

    private Language chosenLanguage;

    private MediaDao mediaDao;

    private Chronometer chronometer;
    private RecordAudio recordAudio;

    private ImageHelper imageHelper;
    private String nameImage;

    private ImageButton btnOptions;

    //private ImageButton btnAudio;
    private RelativeLayout containerAudio;
    private RelativeLayout containerImage;

    private ImageButton btnPhoto;
    private ImageButton btnDeleteImage;
    private ImageView ivImage;
    private String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cadastre_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vocabularyDao = new VocabularyDao(getActivity());
        mediaDao = new MediaDao(getActivity());
        languageDao = new LanguageDao(getActivity());
        wordNotification = new WordNotification(getActivity());
        recordAudio = new RecordAudio(getActivity());
        imageHelper = new ImageHelper(getActivity(), getActivity());

        listLanguage = languageDao.getLanguage();

        String intervalDefault = SharedPreferenceHelper.read(getActivity(), Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.SETTINGS, "");
        settingsObj = new Gson().fromJson(intervalDefault, Settings.class);

        initViews(view);
        fillDetails();

    }

    private void initViews(View view) {
        etWord = (EditText) view.findViewById(R.id.et_word);
        etMeaning = (EditText) view.findViewById(R.id.et_meaning);
        etLanguage = (EditText) view.findViewById(R.id.et_language);
        etIntervalNotification = (EditText) view.findViewById(R.id.et_notification);
        ivImage = (ImageView) view.findViewById(R.id.iv_photo);

        //chronometer = (Chronometer) view.findViewById(R.id.chronometer);

        //btnSave = (Button) view.findViewById(R.id.btn_save);
        llBtnSave = (LinearLayout) view.findViewById(R.id.ll_btn_save);

        btnPhoto = (ImageButton) view.findViewById(R.id.btn_photo);
        //btnAudio = (ImageButton) view.findViewById(R.id.btn_audio);
        btnNewLanguage = (ImageButton) view.findViewById(R.id.ib_new_language);
        btnOptions = (ImageButton) view.findViewById(R.id.ib_options);
        btnDeleteImage = (ImageButton) view.findViewById(R.id.ib_delete_image);

        containerAudio = (RelativeLayout) view.findViewById(R.id.container_audio);
        containerImage = (RelativeLayout) view.findViewById(R.id.container_image);

        etLanguage.setOnClickListener(this);
        etIntervalNotification.setOnClickListener(this);

        llBtnSave.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnDeleteImage.setOnClickListener(this);
        btnOptions.setOnClickListener(this);

        recordAudio.setListener(new RecordAudio.OnFinishRecord() {
            @Override
            public void finishRecord(Media media) {
                mediaRecord = media;
                if(media != null){
                    containerAudio.setVisibility(View.VISIBLE);
                }
            }
        });

//        btnAudio.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_DOWN:
//                        startChronometer();
//                        recordAudio.start();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        stopChronometer();
//                        recordAudio.stop();
//                        break;
//                }
//                return true;
//            }
//        });



    }

    private void fillDetails(){
        int hour = HelperUtil.parseMillisecondsToHour(settingsObj.getIntervalGlobal());
        etIntervalNotification.setText(hour + ":00");
    }

    private void startChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(170); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        chronometer.setTextColor(getResources().getColor(R.color.red_FF0000));
        chronometer.startAnimation(anim);
    }

    private void stopChronometer(){
        chronometer.stop();
        chronometer.setTextColor(getResources().getColor(R.color.gray_757575));
        chronometer.clearAnimation();
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
        hp.show(getFragmentManager(), "hour_dialog");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.btn_save:
            case R.id.ll_btn_save:
                saveWord();
                break;

            case R.id.et_language:
                showLanguageDialog();
                break;

            case R.id.et_notification:
                showHourDialog(v);
                break;

            case R.id.btn_photo:
                takeImage();
                break;

//            case R.id.btn_audio:
//                Log.i("Click","btn_audio");
//                break;

            case R.id.ib_new_language:
                cadastreLanguage();
                break;

            case R.id.ib_options:
                showOptionsRecord();
                break;

            case R.id.ib_delete_image:
                deleteImage();
                break;
        }
    }

    private void displayLanguage(){
            DialogLanguageChoose dl = new DialogLanguageChoose();
            dl.setListLanguages(retrieveCategory(listLanguage));
            dl.setListener(new DialogLanguageChoose.OnDialogLanguageChooseListener() {
                @Override
                public void onLanguageChosen(int position) {
                    chosenLanguage = listLanguage.get(position);
                    etLanguage.setText(chosenLanguage.getName());
                }
            });
            dl.show(getFragmentManager(), "language_dialog");
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
        dp.show(getFragmentManager(), "picture_dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int dimens[] = HelperUtil.getSizeScreen(getActivity());
        int density = HelperUtil.getDensity(getActivity());
        int margin = (int) getActivity().getResources().getDimension(R.dimen.margin_20px);

        int width = dimens[0] - (margin * 2 * density);
        int height = 250 * density;

        if (resultCode == Activity.RESULT_OK) {
            mediaImage = new Media();
            mediaImage.setType(Media.TYPE_IMAGE);

            if (requestCode == Image.REQUEST_IMAGE_CAPTURE) {
                //containerImage.setVisibility(View.VISIBLE);
                Uri source = Uri.fromFile(new File(Constants.Store.PATH_IMG + "/" + nameImage));
                Uri outputUri = Uri.fromFile(new File(Constants.Store.PATH_IMG, "cropped_" + nameImage));

                path = Constants.Store.PATH_IMG + "/" + "cropped_" + nameImage;


                //new Crop(source).output(outputUri).withAspect(ivImage.getMeasuredWidth(), ivImage.getMeasuredHeight()).start(getActivity());
                new Crop(source).output(outputUri).withAspect(width, height).start(getActivity());

            } else if (requestCode == Image.SELECT_FILE) {
                //containerImage.setVisibility(View.VISIBLE);
                String pathTemp = imageHelper.getPath(data.getData());
                String[] splitPath = pathTemp.split("/");
                String name = splitPath[splitPath.length-1];

                Uri source = Uri.fromFile(new File(pathTemp));
                Uri outputUri = Uri.fromFile(new File(Constants.Store.PATH_IMG, "cropped_" + name));

                path = Constants.Store.PATH_IMG + "/" + "cropped_" + name;

                //new Crop(source).output(outputUri).withAspect(ivImage.getMeasuredWidth(), ivImage.getMeasuredHeight()).start(getActivity());
                new Crop(source).output(outputUri).withAspect(width, height).start(getActivity());

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
        Toast.makeText(getActivity(), "Error load image", Toast.LENGTH_SHORT).show();
    }
    /** **/

    private void showOptionsRecord(){
        DialogOptionsRecord options = new DialogOptionsRecord();
        options.setListener(new DialogOptionsRecord.OnOptionsRecordListener() {
            @Override
            public void playAudio() {
                recordAudio.play();
            }

            @Override
            public void deleteAudio() {
                //containerAudio.setVisibility(View.GONE);
                //recordAudio.deleteFileRecord(mediaRecord.getName());
                //mediaRecord = null;
            }
        });
        options.show(getFragmentManager(), "options_dialog");
    }

    private void deleteImage(){
       containerImage.setVisibility(View.GONE);
       mediaImage = null;
    }

    private void saveWord(){
        String word = etWord.getText().toString();
        String meaning = etMeaning.getText().toString();
        String interval = etIntervalNotification.getText().toString();

        long intervalMilliseconds = HelperUtil.parseHourToMilliseconds(interval);

        Vocabulary vocabulary = new Vocabulary(true, word, meaning, intervalMilliseconds, chosenLanguage);

        if (HelperUtil.validateFields(word, meaning)) {
            vocabularyDao.create(vocabulary);

            if(mediaRecord != null){
                mediaRecord.setVocabulary(vocabulary);
                mediaDao.create(mediaRecord);
            }

            if(mediaImage != null){
                mediaImage.setVocabulary(vocabulary);
                mediaDao.create(mediaImage);
            }

            wordNotification.createNotification(vocabulary);
            Toast.makeText(getActivity(), getResources().getString(R.string.txt_saved), Toast.LENGTH_LONG).show();
            setFragmentContent(new MainFragment(), R.id.content_frame, "main_fragment", false);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.msg_validade), Toast.LENGTH_LONG).show();
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

    @Override
    public void onResume() {
        super.onResume();
        configBtnLanguage();
    }

    @Override
    public void onDestroy() {
        recordAudio.release();
        super.onDestroy();
    }

    private void cadastreLanguage(){
        DialogLanguage dl = new DialogLanguage();
        dl.setListener(new DialogLanguage.OnDialogLanguageAddListener() {
            @Override
            public void onLanguageAdd(String language) {
                if(language.trim().isEmpty()){
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_language_empty), Toast.LENGTH_LONG).show();
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
        dl.show(getFragmentManager(), "Alert Cadastre Language");
    }

    private boolean emptyLanguage(){
        boolean empty = true;
        if(languageDao.getAll().size() > 0){
            empty = false;
        }
        return empty;
    }

}
