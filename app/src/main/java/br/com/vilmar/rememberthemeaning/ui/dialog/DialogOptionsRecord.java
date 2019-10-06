package br.com.vilmar.rememberthemeaning.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;

import com.vilmar.rememberthemeaning.app.R;

/**
 * Created by vilmar on 2/19/15.
 */
public class DialogOptionsRecord extends DialogFragment {

    private Integer title;

    private OnOptionsRecordListener listener;

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

        builder.setItems(R.array.option_dialog_record, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(listener != null){
                    switch (which){
                        case 0:
                            listener.playAudio();
                            break;

                        case 1:
                            listener.deleteAudio();
                            break;
                    }
                }
            }
        });

        return builder.create();
    }

    public void setTitle(Integer title){
        this.title = title;
    }

    public void setListener(OnOptionsRecordListener listener){
        this.listener = listener;
    }

    public interface OnOptionsRecordListener{
        public void playAudio();
        public void deleteAudio();
    }
}
