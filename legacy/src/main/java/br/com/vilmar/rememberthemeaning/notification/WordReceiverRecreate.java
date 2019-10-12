package br.com.vilmar.rememberthemeaning.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao;
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary;

/**
 * Created by vilmar on 27/06/14.
 */
public class WordReceiverRecreate extends BroadcastReceiver {

    private List<Vocabulary> vocabularyList;
    private Context context;
    private VocabularyDao vocabularyDao;
    //private PendingIntent alarmIntent;
    private WordNotification wordNotification;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.vocabularyList = new ArrayList<Vocabulary>();

        vocabularyDao = new VocabularyDao(context);
        wordNotification = new WordNotification(context);

        setAlarmPreferences();
    }

    private void setAlarmPreferences() {
        try {
            //vocabularyList = vocabularyDao.getWords();
            vocabularyList = vocabularyDao.getAllByOrder();

            for (Vocabulary reminder : vocabularyList) {
                if (reminder.isActive()) {
                    wordNotification.createNotification(reminder);
                }
            }

        } catch (Exception e) {
            Log.e("setAlarmPreferences", e.getMessage());
        }
    }


}
