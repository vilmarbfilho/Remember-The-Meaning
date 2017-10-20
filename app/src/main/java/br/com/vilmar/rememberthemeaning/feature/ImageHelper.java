package br.com.vilmar.rememberthemeaning.feature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.util.HelperUtil;

/**
 * Created by vilmar on 3/15/15.
 */
public class ImageHelper {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int SELECT_FILE = 2;

    private Context mContext;

    private Activity mActivity;

    public ImageHelper(Context context, Activity mActivity){
        this.mContext = context;
        this.mActivity = mActivity;
    }

    public String generateIdPicture(){
        return "image_" + HelperUtil.getIdByCurrentTime() + ".jpg";
    }

    public void dispatchTakePicture(String idPicture) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f = new File(Constants.Store.PATH_IMG,  idPicture);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

        mActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }


    public void dispatchPictureLibrary(){
        Intent imageLibraryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageLibraryIntent.setType("image/*");

        mActivity.startActivityForResult(Intent.createChooser(imageLibraryIntent, "Select File"), SELECT_FILE);
    }

    public void loadImage(String path, ImageView iv, Callback cb){
        Picasso.with(mContext)
                .load(new File(path))
                .into(iv, cb);
    }

    public String getPath(Uri uri) { //TEST
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = ((Activity)mContext).managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
