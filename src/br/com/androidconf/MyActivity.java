package br.com.androidconf;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {

    private WebView webView;
    private Button button;
    private TextView textView;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        webView = (WebView) findViewById(R.main.webview);
        webView.loadUrl("file:///android_asset/index.html");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(MyActivity.this, message, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        button = (Button) findViewById(R.main.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("javascript:sayHelloFromHTML()");
            }
        });

        webView.addJavascriptInterface(new JavaScriptBrigde(), "androidconf");

        textView = (TextView) findViewById(R.main.textview);
    }
    
    private class JavaScriptBrigde {
        public void sayHelloFromAndroid(final String msg) {
            handler.post(new Runnable() {
                public void run() {
                    textView.setText(msg);
            	}
            });
        }
    }

}
