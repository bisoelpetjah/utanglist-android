package com.anu.utanglist

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.activeandroid.query.Select
import com.anu.utanglist.models.Debt
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget

/**
 * Created by irvan on 2/23/16.
 */
class DebtDetailActivity: AppCompatActivity() {

    companion object {
        final val EXTRA_DEBT_ID = "EXTRA_DEBT_ID"
    }

    private var toolbar: Toolbar? = null
    private var imageViewPhoto: ImageView? = null
    private var textViewAmount: TextView? = null
    private var textViewTotalAmount: TextView? = null
    private var textViewLabelName: TextView? = null
    private var textViewName: TextView? = null
    private var textViewNote: TextView? = null
    private var layoutPayments: LinearLayout? = null
    private var emptyPayment: TextView? = null

    private var debt: Debt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debt_detail)

        toolbar = findViewById(R.id.toolbar) as Toolbar
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
        debt = Select().from(Debt::class.java).where("id = ?", debtId).executeSingle()

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
        textViewAmount?.text = debt?.currentAmount.toString()
        textViewTotalAmount?.text = debt?.totalAmount.toString()
        if (debt?.type == Debt.Type.BORROW) {
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