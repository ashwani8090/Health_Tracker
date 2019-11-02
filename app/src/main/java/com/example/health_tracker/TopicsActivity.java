package com.example.health_tracker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class TopicsActivity extends AppCompatActivity {

    public final String GlobalUrl = "https://www.medicalnewstoday.com/popular/";
    private WebView webView;
    private ProgressBar progressBarWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        webView = findViewById(R.id.webview);
        progressBarWeb=findViewById(R.id.progressBar_web);


        try {

            StartWebView(GlobalUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void StartWebView(String url) {

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressBarWeb.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressBarWeb.setVisibility(View.INVISIBLE);
                Toast.makeText(TopicsActivity.this, "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        webView.loadUrl(url);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
