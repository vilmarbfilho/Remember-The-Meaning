package br.com.vilmar.rememberthemeaning.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

import br.com.vilmar.rememberthemeaning.ui.activity.WebViewActivity;

/**
 * Created by vilmar on 08/07/14.
 */
public class WebAppInterface {

    private Context mContext;

    public WebAppInterface(Context c) {
        this.mContext = c;
    }

    @JavascriptInterface
    public void dismissProgress() {
        ((WebViewActivity) mContext).hideProgessBar();
    }
}
