package br.com.vilmar.rememberthemeaning.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

/**
 * Created by vilmar.filho on 02/02/15.
 */
public class DialogInfo extends DialogFragment{

    private InfoDialogListener infoDialogListener;

    private Integer title;
    private Integer message;
    private Integer messagePositiveButton;
    private Integer messageNegativeButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(getActivity());
        } else {
            builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        }

        if(this.title != null)
            builder.setTitle(title);

        if(this.message != null)
            builder.setMessage(this.message);

        if (this.messagePositiveButton != null)
            builder.setPositiveButton(this.messagePositiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (infoDialogListener != null) {
                        infoDialogListener.onConfirm();
                    }
                }
            });


        if (this.messageNegativeButton != null)
            builder.setNegativeButton(this.messageNegativeButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        return builder.create();
    }

    public void setTitle(Integer title){
        this.title = title;
    }

    public void setMessage(Integer message){
        this.message = message;
    }

    public void setPositiveButton(Integer messagePositiveButton){
        this.messagePositiveButton = messagePositiveButton;
    }

    public void setNegativeButton(Integer messageNegativeButton){
        this.messageNegativeButton = messageNegativeButton;
    }

    public void setListener(InfoDialogListener hourPickerListener) {
        this.infoDialogListener = hourPickerListener;
    }

    public interface InfoDialogListener{
        public void onConfirm();
        public void onCancel();
    }
}
