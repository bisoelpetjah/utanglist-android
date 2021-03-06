package com.anu.utanglist

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.anu.utanglist.fragments.OfferFragment
import com.anu.utanglist.fragments.DemandFragment
import com.anu.utanglist.models.User
import com.anu.utanglist.utils.WebServiceHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by irvan on 2/16/16.
 */
class MainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var drawerHeaderLayout: RelativeLayout? = null
    private var imageViewPhoto: ImageView? = null
    private var textViewName: TextView? = null
    private var buttonAdd: FloatingActionButton? = null

    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (WebServiceHelper.accessToken == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            toolbar = findViewById(R.id.toolbar) as Toolbar
            drawerLayout = findViewById(R.id.drawerLayout) as DrawerLayout
            navigationView = findViewById(R.id.navigationView) as NavigationView
            drawerHeaderLayout = navigationView?.getHeaderView(0)?.findViewById(R.id.drawerHeader) as RelativeLayout
            imageViewPhoto = navigationView?.getHeaderView(0)?.findViewById(R.id.photo) as ImageView
            textViewName = navigationView?.getHeaderView(0)?.findViewById(R.id.name) as TextView
            buttonAdd = findViewById(R.id.add) as FloatingActionButton

            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0)
            drawerLayout?.addDrawerListener(drawerToggle as DrawerLayout.DrawerListener)
            drawerToggle?.syncState()

            navigationView?.setNavigationItemSelectedListener(this)

            navigationView?.setCheckedItem(R.id.drawer_demand)
            supportActionBar?.setTitle(R.string.drawer_demand)
            assignFragment(DemandFragment())

            WebServiceHelper.service!!.getCurrentUser().enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>?, response: Response<User>?) {
                    if (response?.isSuccess!!) setCurrentUser(response?.body())
                }

                override fun onFailure(call: Call<User>?, t: Throwable?) {
                    Toast.makeText(this@MainActivity, R.string.error_connection, Toast.LENGTH_SHORT).show()
                }
            })

            drawerHeaderLayout?.setOnClickListener {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.EXTRA_IS_CURRENT_USER, true)
                startActivity(intent)
            }

            buttonAdd?.setOnClickListener {
                startActivity(Intent(this, AddDebtActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (WebServiceHelper.accessToken == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem?): Boolean {
        drawerLayout?.closeDrawers()

        when (item?.itemId) {
            R.id.drawer_demand -> {
                supportActionBar?.setTitle(R.string.drawer_demand)
                assignFragment(DemandFragment())
                return true
            }
            R.id.drawer_offer -> {
                supportActionBar?.setTitle(R.string.drawer_offer)
                assignFragment(OfferFragment())
                return true
            }
            R.id.drawer_settings-> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return false
            }
            else -> return false
        }
    }

    private fun setCurrentUser(currentUser: User?) {
        Glide.with(this@MainActivity)
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
        textViewName?.text = currentUser?.name
    }

    private fun assignFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}