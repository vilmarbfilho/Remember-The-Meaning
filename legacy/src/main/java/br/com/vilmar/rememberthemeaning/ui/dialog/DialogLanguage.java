package br.com.vilmar.rememberthemeaning.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.vilmar.rememberthemeaning.app.R;

import br.com.vilmar.rememberthemeaning.util.HelperUtil;

/**
 * Created by vilmar on 03/08/14.
 */
public class DialogLanguage extends DialogFragment {

    private OnDialogLanguageAddListener listener;
    private EditText et;

    private int mResourceTitle;
    private String mValue;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(getActivity());
        } else {
            builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.language_dialog, null);
        et = (EditText) v.findViewById(R.id.et_language);

        if(HelperUtil.apiBellowHoneycomb()){
            et.setTextColor(getActivity().getResources().getColor(android.R.color.white)); //TODO fix bug color theme
        }

        if(this.mResourceTitle != 0)
            builder.setTitle(this.mResourceTitle);

        if(this.mValue != null && !this.mValue.isEmpty())
            et.setText(this.mValue);

        builder.setView(v)
                .setPositiveButton(R.string.txt_dialog_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onLanguageAdd(et.getText().toString());
                        }
                    }
                })
                .setNegativeButton(R.string.txt_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onLanguageCancel();
                        }
                    }
                });

        return builder.create();
    }

    public void setTitle(int resourceTitle){
        this.mResourceTitle = resourceTitle;
    }

    public void setValue(String value){
        this.mValue = value;
    }

    public void setListener(OnDialogLanguageAddListener listener){
        this.listener = listener;
    }

    public interface OnDialogLanguageAddListener{
        public void onLanguageAdd(String language);
        public void onLanguageCancel();
    }

}
