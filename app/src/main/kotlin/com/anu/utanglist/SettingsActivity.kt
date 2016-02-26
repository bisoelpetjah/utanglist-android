package com.anu.utanglist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.anu.utanglist.fragments.SettingsFragment

/**
 * Created by irvan on 2/26/16.
 */
class SettingsActivity: AppCompatActivity() {

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar = findViewById(R.id.toolbar) as Toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.title_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment())
                .commit()
    }
}