package br.com.vilmar.rememberthemeaning.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;


/**
 * Created by vilmar on 03/08/14.
 */
public class DialogLanguageChoose extends DialogFragment {

    private String[] listLanguages;
    private OnDialogLanguageChooseListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(getActivity());
        } else {
            builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        }

        //builder.setTitle(R.string.txt_title_language);

        if(this.listLanguages != null)
            builder.setItems(this.listLanguages, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position of the selected item
                    if (listener != null) {
                        listener.onLanguageChosen(which);
                    }
                }
            });

        return builder.create();
    }

    public void setListLanguages(String[] listLanguages){
        this.listLanguages = listLanguages;
    }

    public void setListener(OnDialogLanguageChooseListener listener) {
        this.listener = listener;
    }

    public interface OnDialogLanguageChooseListener{
        public void onLanguageChosen(int position);
    }
}
