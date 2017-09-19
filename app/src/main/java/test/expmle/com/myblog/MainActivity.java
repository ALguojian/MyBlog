package test.expmle.com.myblog;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected WebView webView;
    private long time=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void init() {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webView.setWebViewClient(new AA());

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        settings.setDomStorageEnabled(true); // 开启 DOM缓存
        settings.setDatabaseEnabled(true);   //开启 数据库缓存
        settings.setAppCacheEnabled(true);//开启H5缓存
        settings.setStandardFontFamily("sans-serif");

        //设置H5缓存目录
        settings.setAppCachePath(MainActivity.this.getDir("cache", Context.MODE_PRIVATE).getPath());

        webView.loadUrl("http://guojian.site");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {

            if (webView.canGoBack()){

                webView.goBack();

            }else {
                if ((System.currentTimeMillis() - time) > 3000) {

                    Toast.makeText(MainActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
                    time = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;// 未处理
        }
        return super.onKeyDown(keyCode, event);
    }

    private class AA extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //隐藏状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //隐藏导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        webView = (WebView) findViewById(R.id.webView);
    }
}
