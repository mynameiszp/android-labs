package com.example.lab6;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button newsOne, newsTwo, newsThree, newsFour;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setNews();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setWebView(String url) {
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        setContentView(webView);
    }

    private void setNews() {
        newsOne = findViewById(R.id.news1);
        newsTwo = findViewById(R.id.news2);
        newsThree = findViewById(R.id.news3);
        newsFour = findViewById(R.id.news4);
        webView = new WebView(this);
        newsOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWebView("https://news.sky.com/story/ron-gittins-fantasy-world-flat-secretly-converted-by-tenant-receives-grade-ii-listed-status-13108252");
            }
        });
        newsTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWebView("https://news.sky.com/story/italy-tiny-sicilian-island-giving-away-goats-as-tensions-grow-over-rapidly-expanding-animal-population-13107661");
            }
        });
        newsThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWebView("https://news.sky.com/story/baseball-team-accused-of-fat-shaming-after-naming-mascot-ozempig-after-weight-loss-drug-13104290");
            }
        });
        newsFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWebView("https://news.sky.com/story/waiters-and-waitresses-race-through-streets-of-paris-for-first-time-in-13-years-ahead-of-olympics-13101526");
            }
        });
    }
}