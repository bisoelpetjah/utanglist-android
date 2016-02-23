package com.anu.utanglist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

/**
 * Created by irvan on 2/23/16.
 */
class DebtDetailActivity: AppCompatActivity() {

    companion object {
        final val EXTRA_DEBT_ID = "EXTRA_DEBT_ID"
    }

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debt_detail)

        toolbar = findViewById(R.id.toolbar) as Toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.title_debt_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener {
            finish()
        }
    }
}