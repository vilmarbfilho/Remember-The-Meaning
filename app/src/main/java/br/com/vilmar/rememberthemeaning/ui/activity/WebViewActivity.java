package br.com.vilmar.rememberthemeaning.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.vilmar.rememberthemeaning.app.R;

import br.com.vilmar.rememberthemeaning.Constants;
import br.com.vilmar.rememberthemeaning.web.WebAppInterface;

/**
 * Created by vilmar on 27/06/14.
 */
public class WebViewActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (!NetworkUtil.haveConnection(this)) {
//            setContentView(R.layout.no_connection);
//        } else {
        setContentView(R.layout.webview_activity);
        initViews();
//        }

        getSupportActionBar().setIcon(R.drawable.transparencia_22px);
        getSupportActionBar().setTitle(getResources().getString(R.string.help));

    }

    private void initViews() {
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


        webView.loadUrl(Constants.URL.HELP);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!NetworkUtil.haveConnection(this)) {
//            setContentView(R.layout.no_connection);
//        } else {
//            setContentView(R.layout.webview_activity);
//            initViews();
//        }
    }

    public void hideProgessBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
