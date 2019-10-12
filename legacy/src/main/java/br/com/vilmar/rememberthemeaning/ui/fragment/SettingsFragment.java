package br.com.vilmar.rememberthemeaning.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vilmar.rememberthemeaning.app.R;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.data.database.model.Settings;
import br.com.vilmar.rememberthemeaning.ui.widget.HourPicker;
import br.com.vilmar.rememberthemeaning.util.HelperUtil;
import br.com.vilmar.rememberthemeaning.util.SharedPreferenceHelper;

/**
 * Created by vilmar on 27/06/14.
 */
public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private EditText etIntervalNotification;

    //private Button save;
    private LinearLayout llBtnSave;

    private Settings settingsObj;

    private boolean flagFirstSave = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String settings = HelperUtil.getSettings(getActivity());
        settingsObj = new Gson().fromJson(settings, Settings.class);

        initViews(view);
    }

    private void initViews(View view) {
        etIntervalNotification = (EditText) view.findViewById(R.id.et_notification);
        llBtnSave = (LinearLayout) view.findViewById(R.id.ll_btn_save);
        //save = (Button) view.findViewById(R.id.btn_save);
        //save.setText(HelperUtil.textWithImage(getActivity(), R.string.txt_btn_save, R.drawable.ic_check_white_24dp));

        etIntervalNotification.setOnClickListener(this);
        //save.setOnClickListener(this);
        llBtnSave.setOnClickListener(this);

        int hour = HelperUtil.parseMillisecondsToHour(settingsObj.getIntervalGlobal());
        etIntervalNotification.setText( hour + ":00");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_notification:
                showHourDialog(v);
                break;

            //case R.id.btn_save:
            case R.id.ll_btn_save:
                saveSettings();
                break;
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

    private void saveSettings() {
        String interval = etIntervalNotification.getText().toString();

        if(flagFirstSave){
            flagFirstSave = false;
        }

        settingsObj.setIntervalGlobal(HelperUtil.parseToHour(interval));
        String settingsJson = new Gson().toJson(settingsObj);

        SharedPreferenceHelper.write(getActivity(), Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.SETTINGS, settingsJson);
        Toast.makeText(getActivity(), getResources().getString(R.string.txt_settings_update), Toast.LENGTH_LONG).show();

        setFragmentContent(new MainFragment(), R.id.content_frame, "main_fragment", false);
    }

}
