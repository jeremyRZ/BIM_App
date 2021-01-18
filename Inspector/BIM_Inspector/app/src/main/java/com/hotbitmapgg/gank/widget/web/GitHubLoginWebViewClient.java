package com.hotbitmapgg.gank.widget.web;

import com.hotbitmapgg.gank.ui.activity.LoginGitHubActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public class GitHubLoginWebViewClient extends android.webkit.WebViewClient {

  private Activity mActivity;


  public GitHubLoginWebViewClient(Activity activity) {

    mActivity = activity;
  }


  @Override
  public void onLoadResource(WebView view, String url) {

    super.onLoadResource(view, url);
  }


  @Override
  public boolean shouldOverrideUrlLoading(WebView view, String url) {

    if (url != null && url.startsWith("orpheus")) {
      return true;
    }
    if (url != null && url.startsWith("http")) {
      LoginGitHubActivity.luancher(mActivity, url);
      return true;
    }
    return true;
  }


  @Override
  public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

    return super.shouldInterceptRequest(view, url);
  }


  @Override
  public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

    return super.shouldInterceptRequest(view, request);
  }


  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {

    super.onPageStarted(view, url, favicon);
  }


  @Override
  public void onPageFinished(WebView view, String url) {

    super.onPageFinished(view, url);
  }


  @Override
  public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

    super.onReceivedError(view, errorCode, description, failingUrl);
  }
}
