package br.com.vilmar.rememberthemeaning.ui.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.vilmar.rememberthemeaning.app.R;

import net.simonvt.numberpicker.NumberPicker;

/**
 * Created by vilmar.filho on 30/01/15.
 */
public class HourPicker extends DialogFragment {

    private HourPickerListener hourPickerListener;

    private NumberPicker np;

    private Integer hour;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(getActivity());
        } else {
            builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View npView = inflater.inflate(R.layout.hour_picker_dialog, null);
        np = (NumberPicker) npView.findViewById(R.id.numberPicker);

        np.setMaxValue(24);
        np.setMinValue(1);
        np.setFocusable(true);
        np.setFocusableInTouchMode(false);

        if(hour != null){
            np.setValue(hour);
        }

        builder.setView(npView)
                .setTitle(R.string.title_dialog_interval)
                .setMessage(R.string.msg_dialog_interval)
                .setPositiveButton(R.string.btn_dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (hourPickerListener != null) {
                            hourPickerListener.OnConfirm(np.getValue());
                        }
                    }
                })
                .setNegativeButton(R.string.btn_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (hourPickerListener != null) {
                            hourPickerListener.OnCancel(np.getValue());
                        }
                    }
                });

        return builder.create();
    }

    public void setHour(int hour){
        this.hour = hour;
    }

    public void setListener(HourPickerListener hourPickerListener) {
        this.hourPickerListener = hourPickerListener;
    }

    public interface HourPickerListener{
        public void OnConfirm(int hour);
        public void OnCancel(int hour);
    }
}
