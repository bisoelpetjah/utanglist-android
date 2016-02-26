package com.anu.utanglist

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.anu.utanglist.models.Token
import com.anu.utanglist.utils.WebServiceHelper
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by irvan on 2/17/16.
 */
class LoginActivity: AppCompatActivity(), FacebookCallback<LoginResult> {

    var imageViewIcon: ImageView? = null
    var textViewBrand: TextView? = null
    var buttonLogin: LoginButton? = null
    var progressBarLogin: ProgressBar? = null

    var isActive: Boolean = true

    val callbackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onSuccess(loginResult: LoginResult?) {
        buttonLogin?.visibility = View.GONE
        progressBarLogin?.visibility = View.VISIBLE

        WebServiceHelper.service!!.login(loginResult?.accessToken?.token!!).enqueue(object: Callback<Token> {
            override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
                if (!response?.isSuccess!!) {
                    Toast.makeText(this@LoginActivity, R.string.error_login, Toast.LENGTH_SHORT).show()

                    buttonLogin?.visibility = View.VISIBLE
                    progressBarLogin?.visibility = View.GONE
                } else {
                    val token = response?.body()

                    WebServiceHelper.accessToken = token?.accessToken

                    PreferenceManager.getDefaultSharedPreferences(this@LoginActivity).edit()
                            .putString(Token.PREF_ACCESS_TOKEN, token?.accessToken)
                            .commit()

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                LoginManager.getInstance().logOut()
            }

            override fun onFailure(call: Call<Token>?, t: Throwable?) {
                Toast.makeText(this@LoginActivity, R.string.error_connection, Toast.LENGTH_SHORT).show()

                buttonLogin?.visibility = View.VISIBLE
                progressBarLogin?.visibility = View.GONE

                LoginManager.getInstance().logOut()
            }
        })
    }

    override fun onCancel() {}

    override fun onError(exception: FacebookException?) {
        Toast.makeText(this, R.string.error_login, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        imageViewIcon = findViewById(R.id.icon) as ImageView
        textViewBrand = findViewById(R.id.brand) as TextView
        buttonLogin = findViewById(R.id.loginButton) as LoginButton
        progressBarLogin = findViewById(R.id.progressLogin) as ProgressBar

        Handler().postDelayed({
            if (isActive) {
                imageViewIcon?.animate()?.translationY((-80f * (Resources.getSystem().displayMetrics.densityDpi / 160f)))
                textViewBrand?.animate()?.translationY((-90f * (Resources.getSystem().displayMetrics.densityDpi / 160f)))?.scaleX(0.7f)?.scaleY(0.7f)
                buttonLogin?.animate()?.scaleX(1.2f)?.scaleY(1.2f)?.alpha(1f)
            }
        }, 1500)

        buttonLogin?.registerCallback(callbackManager, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        isActive = false
    }
}