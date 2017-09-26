package com.valensas.littlehut.login

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.valensas.littlehut.network.APIService


/**
 * Created by ogun on 19/09/2017.
 * Valensas 2017
 */
class WebViewController : WebViewClient() {

    var listener: WebViewOnFinishListener? = null

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url!!.contains("?token=")) {
            listener?.onLoginCompleted(url.substring(APIService.SLACK_REDIRECTION_URL.length + 7))
        } else {
            view?.loadUrl(url)
        }
        return true
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        if (url.contains("?token=")) {
            listener?.onLoginCompleted(url.substring(APIService.SLACK_REDIRECTION_URL.length + 7))
        } else {
            view?.loadUrl(url)
        }


        return true
    }

    fun setOnFinishListener(listener: WebViewOnFinishListener) {
        this.listener = listener
    }
}