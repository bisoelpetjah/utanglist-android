package com.anu.utanglist

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.Toast
import com.anu.utanglist.fragments.adapters.ProfilePagerAdapter
import com.anu.utanglist.models.User
import com.anu.utanglist.utils.WebServiceHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by irvan on 2/27/16.
 */
class ProfileActivity: AppCompatActivity() {

    companion object {
        final val EXTRA_IS_CURRENT_USER = "EXTRA_IS_CURRENT_USER"
    }

    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var toolbar: Toolbar? = null
    private var tabLayout: TabLayout? = null
    private var imageViewPhoto: ImageView? = null
    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar) as CollapsingToolbarLayout
        toolbar = findViewById(R.id.toolbar) as Toolbar
        tabLayout = findViewById(R.id.tabs) as TabLayout
        imageViewPhoto = findViewById(R.id.photo) as ImageView
        viewPager = findViewById(R.id.viewPager) as ViewPager

        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        viewPager?.offscreenPageLimit = 4
        viewPager?.adapter = ProfilePagerAdapter(this, supportFragmentManager)
        tabLayout?.setupWithViewPager(viewPager!!)

        val isCurrentUser = intent.getBooleanExtra(EXTRA_IS_CURRENT_USER, false)
        if (isCurrentUser) performGetCurrentUser()
    }

    private fun performGetCurrentUser() {
        WebServiceHelper.service!!.getCurrentUser().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                if (response?.isSuccess!!) setUser(response?.body())
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Toast.makeText(this@ProfileActivity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUser(currentUser: User?) {
        Glide.with(this)
                .load(currentUser?.photoUrl)
                .asBitmap()
                .placeholder(R.drawable.ic_profile_placeholder)
                .centerCrop()
                .into(object: BitmapImageViewTarget(imageViewPhoto) {
                    override fun setResource(resource: Bitmap?) {
                        val bitmapDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, resource)
                        bitmapDrawable.isCircular = true
                        imageViewPhoto?.setImageDrawable(bitmapDrawable)
                    }
                })
        supportActionBar?.title = currentUser?.name
    }
}