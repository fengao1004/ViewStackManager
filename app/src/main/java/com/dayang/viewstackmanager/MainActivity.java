package com.dayang.viewstackmanager;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] urls = {"http://www.baidu.com", "http://www.youku.com", "http://www.jd.com", "http://www.tb.com", "https://github.com/", "http://www.oschina.net/", "https://www.zhihu.com/", "http://dig.chouti.com/"};
    private ImageView delete;
    private ImageView add;
    private TextView index;
    private TextView page;
    private TextView show;
    private FrameLayout fl;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        delete = (ImageView) findViewById(R.id.delete);
        add = (ImageView) findViewById(R.id.add);
        index = (TextView) findViewById(R.id.index);
        page = (TextView) findViewById(R.id.page);
        show = (TextView) findViewById(R.id.show);
        fl = (FrameLayout) findViewById(R.id.fl);
        LayoutTransition transition = new LayoutTransition();
        fl.setLayoutTransition(transition);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(null, "translationX", width,0).setDuration(1000);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(null, "translationX",0,width).setDuration(1000);
        transition.setAnimator(LayoutTransition.APPEARING, animator1);
        transition.setAnimator(LayoutTransition.DISAPPEARING, animator2);
        fl.setLayoutTransition(transition);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebView webView = getWebView();
                webView.loadUrl(urls[fl.getChildCount()%8]);
                String[] split = urls[fl.getChildCount()% 8].split("\\.");
                page.setText(split[split.length-2]);
                index.setText(fl.getChildCount()+"");
                fl.addView(webView);
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.getChildAt(3).bringToFront();
                fl.requestLayout();
                fl.invalidate();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fl.getChildCount()>=1){
                    fl.removeViewAt(fl.getChildCount()-1);
                    String[] split = urls[fl.getChildCount()].split(".");
//                    page.setText(split[split.length-1]);
                    index.setText(fl.getChildCount()+"");
                }
            }
        });
    }

    public WebView getWebView() {
        WebView webView = new WebView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return webView;
    }
}
