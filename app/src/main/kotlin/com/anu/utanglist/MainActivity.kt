package com.anu.utanglist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.anu.utanglist.utils.WebServiceHelper

/**
 * Created by irvan on 2/16/16.
 */
class MainActivity: AppCompatActivity() {

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (WebServiceHelper.accessToken == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        toolbar = findViewById(R.id.toolbar) as Toolbar

        setSupportActionBar(toolbar)
    }
}