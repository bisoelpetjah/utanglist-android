package com.anu.utanglist

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
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
    private var textViewTotalAmount: TextView? = null
    private var textViewLabelName: TextView? = null
    private var textViewName: TextView? = null
    private var textViewNote: TextView? = null
    private var layoutPayments: LinearLayout? = null
    private var emptyPayment: TextView? = null

    private var debt: Debt? = null
    private var debtType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debt_detail)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        progressBarLoading = findViewById(R.id.loading) as ProgressBar
        layoutContainer = findViewById(R.id.container) as ScrollView
        imageViewPhoto = findViewById(R.id.photo) as ImageView
        textViewAmount = findViewById(R.id.amount) as TextView
        textViewTotalAmount = findViewById(R.id.totalAmount) as TextView
        textViewLabelName = findViewById(R.id.labelName) as TextView
        textViewName = findViewById(R.id.name) as TextView
        textViewNote = findViewById(R.id.note) as TextView
        layoutPayments = findViewById(R.id.payments) as LinearLayout
        emptyPayment = findViewById(R.id.emptyPayment) as TextView

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.title_debt_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        val debtId = intent.getStringExtra(EXTRA_DEBT_ID)
        debtType = intent.getStringExtra(EXTRA_DEBT_TYPE)

        if (debtType == Debt.TYPE_DEMAND) {
            performGetDebtDemandById(debtId)
        } else {
            performGetDebtOfferById(debtId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail, menu)

        if (debtType == Debt.TYPE_DEMAND) {
            menu?.findItem(R.id.pay)?.isVisible = true
        } else {
            menu?.findItem(R.id.pay)?.isVisible = false
        }

        return super.onCreateOptionsMenu(menu)
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
        textViewTotalAmount?.text = debt?.amount.toString()
        if (debtType == Debt.TYPE_DEMAND) {
            textViewAmount?.setTextColor(ContextCompat.getColor(this, R.color.bg_floating_action_button))
            textViewLabelName?.text = resources.getString(R.string.label_detail_borrow)
        } else {
            textViewAmount?.setTextColor(ContextCompat.getColor(this, android.R.color.primary_text_light))
            textViewLabelName?.text = resources.getString(R.string.label_detail_lend)
        }
        textViewName?.text = debt?.user?.name
        textViewNote?.text = debt?.note

        if (debt?.paymentList!!.isEmpty()) {
            emptyPayment?.visibility = View.VISIBLE
        } else {
            emptyPayment?.visibility = View.GONE
        }
    }
}