package br.com.vilmar.rememberthemeaning.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.database.model.Vocabulary;

/**
 * Created by vilmar on 08/07/14.
 */
public class WordNotification {

    public static final String NOTIFICATION = "notification";

    private Context mContext;
    private PendingIntent alarmIntent;
    private Long totalTimeMiliseconds;

    public WordNotification(Context c) {
        this.mContext = c;
    }

    public void createNotification(Vocabulary vocabulary) {
        if(!vocabulary.isActive())

            return;

        AlarmManager alarmManager = (AlarmManager) mContext.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext.getApplicationContext(), WordReceiver.class);
        Date today = Calendar.getInstance().getTime();

        if(Constants.APP_HOMOLOGACAO){
            totalTimeMiliseconds = today.getTime(); //DEBUG_HMG
        } else {
            totalTimeMiliseconds = today.getTime() + vocabulary.getInterval();
        }

        intent.putExtra(NOTIFICATION, vocabulary);

        alarmIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), vocabulary.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, totalTimeMiliseconds, vocabulary.getInterval(), alarmIntent);

    }

    public void deleteNotification(Vocabulary vocabulary) {
        AlarmManager am = (AlarmManager) mContext.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext.getApplicationContext(), WordReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(mContext.getApplicationContext(), vocabulary.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.cancel(pi);
    }
}
