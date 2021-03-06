package com.example.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * Created by 肖坤 on 2019/1/29.
 *
 * @author 肖坤
 * @date 2019/1/29
 */

public class ProgressWebView extends FrameLayout {

    private static final String TAG = "ProgressWebView";
    private Context mContext;
    private int DEFALUT_BG_COLOR = R.color.color_ccc;
    private int DEFALUT_BG_PROGRESS_COLOR = R.color.color_33E574;

    private int mProgressBarHeight;
    private int mProgressBarBgColor;
    private int mProgressBarProgressColor;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private ProgressWebViewClient mWebViewClient;

    public ProgressWebView(@NonNull Context context) {
        this(context, null);
    }

    public ProgressWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressWebView);
            if (array != null) {
                mProgressBarHeight = (int) array.getDimension(R.styleable.ProgressWebView_progressbar_height, dpToPxInt(2));
                mProgressBarBgColor = array.getColor(R.styleable.ProgressWebView_progressbar_bg_color,
                        ContextCompat.getColor(mContext, DEFALUT_BG_COLOR));
                mProgressBarProgressColor = array.getColor(R.styleable.ProgressWebView_progressbar_progress_color,
                        ContextCompat.getColor(mContext, DEFALUT_BG_PROGRESS_COLOR));
                array.recycle();
            }
        }
        LayoutInflater.from(context).inflate(R.layout.progress_webview, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mWebView = findViewById(R.id.progress_webview);
        mProgressBar = findViewById(R.id.progress_bar);

        //设置bar height
        ViewGroup.LayoutParams layoutParams = mProgressBar.getLayoutParams();
        layoutParams.height = mProgressBarHeight;
        mProgressBar.setLayoutParams(layoutParams);

        //设置背景颜色
        Drawable bckgrndDr = new ColorDrawable(mProgressBarBgColor);
        Drawable progressDr = new ScaleDrawable(new ColorDrawable(mProgressBarProgressColor), Gravity.LEFT, 1, -1);
        LayerDrawable resultDr = new LayerDrawable(new Drawable[]{bckgrndDr, progressDr});
        resultDr.setId(0, android.R.id.background);
        resultDr.setId(1, android.R.id.progress);
        mProgressBar.setProgressDrawable(resultDr);

        initWvDefault();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWvDefault() {
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
                if (progress < 100 && mProgressBar.getVisibility() == ProgressBar.GONE) {
                    mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
                mProgressBar.setProgress(progress);
                if (progress == 100) {
                    mProgressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });

        mWebViewClient = new ProgressWebViewClient();
        mWebView.setWebViewClient(mWebViewClient);
    }

    /**
     * 如果有对webview更多的设置,调用此方法进行设置
     *
     * @return
     */
    public WebView getWebView() {
        return mWebView;
    }

    public void loadUrl(String url) {
        if (!TextUtils.isEmpty(url) && mWebView != null) {
            mWebView.loadUrl(url);
        }
    }

    public ProgressWebViewClient getWebViewClient() {
        return mWebViewClient;
    }

    public void setWebViewClient(ProgressWebViewClient webViewClient) {
        if (mWebView != null) {
            if (webViewClient != null) {
                mWebView.setWebViewClient(webViewClient);
                mWebViewClient = webViewClient;
            } else {
                mWebView.setWebViewClient(mWebViewClient);
            }
        }
    }

    private float dpToPx(float dp) {
        if (mContext != null) {
            return dp * mContext.getResources().getDisplayMetrics().density;
        }
        return 0;
    }

    private int dpToPxInt(float dp) {
        if (mContext != null) {
            return (int) (dpToPx(dp) + 0.5f);
        }
        return 0;
    }

    /**
     * 封装错误状态页面
     */
    class ProgressWebViewClient extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.e(TAG, "error:" + error.toString());
        }
    }


}
