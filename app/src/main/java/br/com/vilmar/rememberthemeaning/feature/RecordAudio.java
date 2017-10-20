package br.com.vilmar.rememberthemeaning.feature;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import com.vilmar.rememberthemeaning.app.R;

import java.io.IOException;
import java.util.Calendar;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.database.model.Media;
import br.com.vilmar.rememberthemeaning.util.HelperUtil;
import br.com.vilmar.rememberthemeaning.util.IoHelper;

/**
 * Created by vilmar on 18/02/15.
 */
public class RecordAudio {

    private Context mContext;

    private MediaRecorder mRecorder;
    private String outputFile = null;

    private MediaPlayer mPlayer;

    //private String idRecording;
    private Media mediaRecord;

    private OnFinishRecord listener;

    public RecordAudio(Context context){
        mContext = context;
        configRecording();
    }

    private void configRecording(){
        mediaRecord = new Media();
        String idRecording = HelperUtil.getIdByCurrentTime();

        outputFile = Constants.Store.PATH_AUDIO +  "/record_" + idRecording + ".3gp";

        //mediaRecord.setPath(outputFile);
        //mediaRecord.setType(Media.AUDIO);

    }

    public void start(){
        startBip(R.raw.beep08);
    }

    private void startBip(int file){
        AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(file);
        final MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.release();
                    startRecording();
                }
            });
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startRecording() {
        if (mRecorder != null) {
            mRecorder.release();
        }

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        mRecorder.setOutputFile(outputFile);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e("startRecording", e.getMessage());
        }
    }


    public void stop(){
        try {
            mRecorder.stop();
            //myRecorder.release();
            //myRecorder  = null;
            if(listener != null){
                listener.finishRecord(mediaRecord);
            }

        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
        }
    }

    public void play() {
        try{
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(outputFile);
            mPlayer.prepare();
            mPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        try {
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void release(){
        try {
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }

            if (mRecorder != null) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFileRecord(String path){
        IoHelper.deleteFile(path);
    }

    public void setListener(OnFinishRecord listener){
        this.listener = listener;
    }

    public interface OnFinishRecord{
        public void finishRecord(Media media);
    }

}
