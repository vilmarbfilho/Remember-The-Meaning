package br.com.vilmar.rememberthemeaning.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.vilmar.rememberthemeaning.app.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao;
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary;
import br.com.vilmar.rememberthemeaning.ui.activity.RememberActivity;
import br.com.vilmar.rememberthemeaning.util.SharedPreferenceHelper;
import br.com.vilmar.rememberthemeaning.util.TinyDB;

/**
 * Created by vilmar on 27/06/14.
 */
public class WordReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION = "notification";

    private Context context;
    private Intent intent;
    private VocabularyDao vocabularyDao;
    private Bundle bundle;

    private long firstNotification;
    private long currentTime;
    private boolean warning = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        vocabularyDao = new VocabularyDao(context);
        bundle = new Bundle();

        configCycleNotification();
        setNotification(context);
    }

    private void configCycleNotification(){
        firstNotification = SharedPreferenceHelper.read(this.context, Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.CYCLE_NOTIFICATION, 0);
        currentTime = Calendar.getInstance().getTime().getTime();
        long differenceTime = currentTime - firstNotification;
        if(differenceTime > 300000){ // 300000 = 5minutes
            warning = true;
            SharedPreferenceHelper.write(this.context, Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.CYCLE_NOTIFICATION, currentTime);
        }
    }

    private void setNotification(Context context) {
        Vocabulary vocabulary = (Vocabulary) intent.getSerializableExtra(NOTIFICATION);

        if (pendingReminders(vocabulary.getId())) {
            countNotification();
        }

        Intent intent = new Intent(context, RememberActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_dolphin_white)
                .setColor(context.getResources().getColor(R.color.blue_3366CC))
                .setContentTitle(this.context.getResources().getString(R.string.title_notification))
                .setContentText(this.context.getResources().getString(R.string.text_notification))
                .setNumber(getCountNotification());

        mBuilder.setContentIntent(pIntent);

        long[] pattern = {0, 100, 1000};

        if(warning){
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        }

        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }

    private void countNotification() {
        int count = (int) SharedPreferenceHelper.read(context, Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.COUNTNOTIFICATION, 0);
        count++;
        SharedPreferenceHelper.write(context, Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.COUNTNOTIFICATION, count);
    }

    private int getCountNotification() {
        return (int) SharedPreferenceHelper.read(context, Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.COUNTNOTIFICATION, 0);
    }

    private boolean pendingReminders(int id) {
        TinyDB tinydb = new TinyDB(context);
        boolean save = false;
        List<Integer> reminders = tinydb.getListInt(Constants.SharedPreferencesKeys.ARRAYREMINDERS, context);

        if (!reminders.contains(id)) {
            reminders.add(id);

            tinydb.putListInt(Constants.SharedPreferencesKeys.ARRAYREMINDERS, new ArrayList<Integer>(reminders), context);
            save = true;
        }
        return save;
    }
}
