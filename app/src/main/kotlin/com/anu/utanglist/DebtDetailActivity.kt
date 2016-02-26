package com.anu.utanglist

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import com.anu.utanglist.models.Debt
import com.anu.utanglist.utils.WebServiceHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by irvan on 2/23/16.
 */
class DebtDetailActivity: AppCompatActivity() {

    companion object {
        final val EXTRA_DEBT_ID = "EXTRA_DEBT_ID"
        final val EXTRA_DEBT_TYPE = "EXTRA_DEBT_TYPE"
    }

    private var toolbar: Toolbar? = null
    private var progressBarLoading: ProgressBar? = null
    private var layoutContainer: ScrollView? = null
    private var imageViewPhoto: ImageView? = null
    private var textViewAmount: TextView? = null
    private var textViewName: TextView? = null
    private var textViewNote: TextView? = null
    private var buttonDebt: Button? = null

    private var debtType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debt_detail)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        progressBarLoading = findViewById(R.id.loading) as ProgressBar
        layoutContainer = findViewById(R.id.container) as ScrollView
        imageViewPhoto = findViewById(R.id.photo) as ImageView
        textViewAmount = findViewById(R.id.amount) as TextView
        textViewName = findViewById(R.id.name) as TextView
        textViewNote = findViewById(R.id.note) as TextView
        buttonDebt = findViewById(R.id.debt) as Button

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        val debtId = intent.getStringExtra(EXTRA_DEBT_ID)
        debtType = intent.getStringExtra(EXTRA_DEBT_TYPE)

        if (debtType == Debt.TYPE_DEMAND) {
            supportActionBar?.setTitle(R.string.title_debt_detail_demand)
            buttonDebt?.setText(R.string.button_debt_demand)
            performGetDebtDemandById(debtId)
        } else {
            supportActionBar?.setTitle(R.string.title_debt_detail_offer)
            buttonDebt?.setText(R.string.button_debt_offer)
            performGetDebtOfferById(debtId)
        }
    }

    private fun performGetDebtDemandById(debtId: String) {
        progressBarLoading?.visibility = View.VISIBLE
        layoutContainer?.visibility = View.GONE

        WebServiceHelper.service!!.getDebtDemandById(debtId).enqueue(object: Callback<Debt> {
            override fun onResponse(call: Call<Debt>?, response: Response<Debt>?) {
                progressBarLoading?.visibility = View.GONE
                layoutContainer?.visibility = View.VISIBLE

                if (response?.isSuccess!!) setCurrentDebt(response?.body())
            }

            override fun onFailure(call: Call<Debt>?, t: Throwable?) {
                progressBarLoading?.visibility = View.GONE
                layoutContainer?.visibility = View.GONE

                Toast.makeText(this@DebtDetailActivity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun performGetDebtOfferById(debtId: String) {
        progressBarLoading?.visibility = View.VISIBLE
        layoutContainer?.visibility = View.GONE

        WebServiceHelper.service!!.getDebtOfferById(debtId).enqueue(object: Callback<Debt> {
            override fun onResponse(call: Call<Debt>?, response: Response<Debt>?) {
                progressBarLoading?.visibility = View.GONE
                layoutContainer?.visibility = View.VISIBLE

                if (response?.isSuccess!!) setCurrentDebt(response?.body())
            }

            override fun onFailure(call: Call<Debt>?, t: Throwable?) {
                progressBarLoading?.visibility = View.GONE
                layoutContainer?.visibility = View.GONE

                Toast.makeText(this@DebtDetailActivity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setCurrentDebt(debt: Debt?) {
        Glide.with(this)
                .load(debt?.user?.photoUrl)
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
        textViewAmount?.text = debt?.amount.toString()
        textViewName?.text = debt?.user?.name
        textViewNote?.text = debt?.note
    }
}