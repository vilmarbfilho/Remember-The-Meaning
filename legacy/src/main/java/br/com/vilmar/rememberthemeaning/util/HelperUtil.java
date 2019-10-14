package br.com.vilmar.rememberthemeaning.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;

import com.vilmar.rememberthemeaning.app.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.vilmar.rememberthemeaning.Constants;

/**
 * Created by vilmar on 27/06/14.
 */
public class HelperUtil {

    public static long parseHourToMilliseconds(String time){
        String[] split = time.split(":");
        int hour = Integer.parseInt(split[0]);
        return TimeUnit.HOURS.toMillis(hour);

    }

    public static int parseToHour(String hour){
        String[] split = hour.split(":");
        return Integer.parseInt(split[0]);
    }

    public static int parseMillisecondsToHour(long milliseconds){
        return (int)milliseconds/3600000;
    }

    public static boolean validateFields(String... string ){
        for(String s : string){
            if(s.trim().isEmpty() || s.trim().length() < 0){
                return false;
            }
        }
        return true;
    }

    public static String getIdByCurrentTime(){
        return Long.toString(Calendar.getInstance().getTime().getTime());
    }

    public static int getColor(String str){
        char[] ch  = str.toCharArray();
        int number = 1;
        int pos = 0;
        int colors[] = {
                R.color.red_FF0000,
                R.color.orange_FE9A2E,
                R.color.blue_2E9AFE,
                R.color.green_298A08,
                R.color.pink_FF00FF,
                R.color.purple_610B5E,
                R.color.red_CD5C5C,
                R.color.green_99FF66,
                R.color.orage_FFCD82,
                R.color.marine_338585
        };
        List<Integer> intArr = new ArrayList();


        for(char c : ch)
        {
            int temp = (int)c;
            int temp_integer = 64; //for upper case
            if(temp <= 90 & temp >= 65)
                number = temp - temp_integer;
        }

        while (number > 0) {
            intArr.add(number % 10);
            number /= 10;
        }

        for (int n : intArr){
            pos += n;
        }

        return colors[pos-1];
    }


    public static String getSettings(Context context){
        return SharedPreferenceHelper.read(context, Constants.SharedPreferencesKeys.SHAREDPREF, Constants.SharedPreferencesKeys.SETTINGS, "");
    }

//    public static SpannableString textWithImage(Context context, int textResource, int imgResource){
//        String txt = "x " + context.getResources().getString(textResource);
//        SpannableString ss = new SpannableString(txt);
//        //Drawable d = context.getResources().getDrawable(imgResource);
//        //d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//        //ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
//        //ss.setSpan(span, (txt.length() - 3), txt.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//
//
//        //ss.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//        ss.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 1, 0);
//
//        return ss;
//    }

    public static int[] getSizeScreen(Activity mActivity){
        int arrDimension[] = new int[2];

        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        //int dens=dm.densityDpi;

        arrDimension[0] = width;
        arrDimension[1] = height;

        return arrDimension;
    }

    public static int getDensity(Activity mActivity){
        float scale = mActivity.getResources().getDisplayMetrics().density;
        return (int) (1 * scale + 0.5f);
    }

    public static boolean apiBellowHoneycomb(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
           return true;
        } else {
            return false;
        }
    }
}
