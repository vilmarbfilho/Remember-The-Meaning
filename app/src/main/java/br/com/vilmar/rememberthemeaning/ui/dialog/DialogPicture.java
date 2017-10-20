package br.com.vilmar.rememberthemeaning.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.vilmar.rememberthemeaning.app.R;

/**
 * Created by vilmar on 2/20/15.
 */
public class DialogPicture extends DialogFragment {

    private OnDialogPictureListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(getActivity());
        } else {
            builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        }

        builder.setTitle(R.string.title_dialog_picture);
        builder.setItems(R.array.option_dialog_picture, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                if(mListener != null){
                    switch (which){
                        case 0:
                            mListener.getTakePhoto();
                            break;

                        case 1:
                            mListener.getImage();
                            break;
                    }
                }
            }
        });

        return builder.create();
    }

    public void setListener(OnDialogPictureListener mListener){
        this.mListener = mListener;
    }

    public interface OnDialogPictureListener{
        public void getTakePhoto();
        public void getImage();
    }
}
