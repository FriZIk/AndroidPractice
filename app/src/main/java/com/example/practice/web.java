package com.example.practice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class web extends AppCompatActivity
{
    private static final String Tag = "Практика";
    private WebView webView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        toolbar = findViewById(R.id.toolbar_web);
        setSupportActionBar(toolbar);

        webView = findViewById(R.id.Site);
        webView.setWebViewClient(new MyWebViewClient());

        if (savedInstanceState != null)
        {
            webView.restoreState(savedInstanceState);
        }
        // Принимаем Индекс и по нему вызываем нужный сайт
        int txtName = getIntent().getIntExtra("Index",-1);
        String Sites[] = getResources().getStringArray(R.array.Sites);
        Log.i(Tag,Sites[txtName]);
        String Logins[] = getResources().getStringArray(R.array.Logins);
        setTitle("DIY helper:" + Logins[txtName]);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Sites[txtName]);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onBackPressed()
    {
        if(webView.canGoBack())webView.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //Intent intent = new Intent(web.this, MainActivity.class);
        //startActivity(intent);
    }
}