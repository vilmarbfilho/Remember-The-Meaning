package br.com.vilmar.rememberthemeaning.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import br.com.vilmar.rememberthemeaning.data.database.model.Media;

/**
 * Created by vilmar on 2/20/15.
 */
public class Image {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int SELECT_FILE = 2;

    private Activity mActivity;
    private Fragment mFragment;

    private Context mContext;

    private String mIdPhoto;

    private Media mediaImage;

    public Image(Context context, Activity mActivity){
        this.mActivity = mActivity;
        this.mContext = context;
        generateId();
    }

    public Image(Context context, Fragment mFragment){
        this.mFragment = mFragment;
        this.mContext = context;
        generateId();
    }

    private void generateId(){
        mIdPhoto = "image_" + HelperUtil.getIdByCurrentTime() + ".jpg";
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f = new File(android.os.Environment.getExternalStorageDirectory(),  mIdPhoto);
        //File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        //File f = new File(Constants.Store.PATH_IMG, "img_" + mIdPhoto + ".jpg");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

        if(mActivity != null){
            mActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            mFragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

//        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
//            ((Activity) mContext).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
    }

    public void dispatchImageLibrary(){
        Intent imageLibraryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageLibraryIntent.setType("image/*");

        if(mActivity != null){
            mActivity.startActivityForResult(Intent.createChooser(imageLibraryIntent, "Select File"), SELECT_FILE);
        } else {
            mFragment.startActivityForResult(Intent.createChooser(imageLibraryIntent, "Select File"), SELECT_FILE);
        }
    }

    public Media processPhotoImage(final RelativeLayout rl, final ImageView iv){
        mediaImage = new Media();
        File f = new File(Environment.getExternalStorageDirectory().toString());

        for (File temp : f.listFiles()) {
            if (temp.getName().equals(mIdPhoto)) {
                f = temp;
                break;
            }
        }

        final String path = f.getPath();

        //resizeImage(path);

        if(f != null){
            Picasso.with(mContext)
                    .load(f)
                    .resize(1000, 1333) //improve it
                    .into(iv, new Callback() {
                        @Override
                        public void onSuccess() {
                            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            rl.setVisibility(View.VISIBLE);
                            //mediaImage.setPath(path);
                            //mediaImage.setType(Media.IMAGE);
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT);
                        }
                    });
        }

        return mediaImage;
    }

    public Media processFileImage(Uri uri, final RelativeLayout rl, final ImageView iv){
        mediaImage = new Media();
        Activity act =  (mActivity != null) ? mActivity : mFragment.getActivity();

        final String tempPath = getPath(uri, act);

        Picasso.with(mContext)
                .load(new File(tempPath))
                .resize(1000, 1333) //improve it
                .into(iv, new Callback() {
                    @Override
                    public void onSuccess() {
                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        rl.setVisibility(View.VISIBLE);
                        //mediaImage.setPath(tempPath);
                        //mediaImage.setType(Media.IMAGE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT);
                    }
                });

        return mediaImage;

    }

    private String getPath(Uri uri, Activity activity) { //TEST
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private Bitmap resizeImage(String path){
        final BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = 8;
        return BitmapFactory.decodeFile(path, options2);
    }
}
