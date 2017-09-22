package com.valensas.littlehut.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.valensas.littlehut.R
import com.valensas.littlehut.busattendance.MainActivity
import com.valensas.littlehut.network.APIService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), WebViewOnFinishListener {

    val PREFS_FILENAME = "com.valensas.littlehut.prefs"
    val KEY_TOKEN = "token"

    lateinit var preferences: SharedPreferences

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        preferences = this.getSharedPreferences(PREFS_FILENAME, 0)
        val currentToken = preferences.getString(KEY_TOKEN, "")
        val tokenExpired = intent.getBooleanExtra(KEY_EXPIRATION, false)

        if (TextUtils.isEmpty(currentToken) || tokenExpired) {

            setContentView(R.layout.activity_login)

            authWebView.settings.javaScriptEnabled = true
            val controller = WebViewController()
            controller.setOnFinishListener(this)
            authWebView.webViewClient = controller
            authWebView.loadUrl(APIService.BASE_URL + APIService.SLACK_AUTH)
        } else {
            APIService.TOKEN = currentToken
            startActivity(MainActivity.newIntent(this))
            overridePendingTransition(0, 0)
            finish()
        }
    }

    override fun onLoginCompleted(token: String) {
        APIService.TOKEN = token
        preferences.edit().putString(KEY_TOKEN, token).apply()
        startActivity(MainActivity.newIntent(this))
        finish()
    }

    companion object {
        val KEY_EXPIRATION = "expiredToken"

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(KEY_EXPIRATION, true)
            return intent
        }
    }
}
