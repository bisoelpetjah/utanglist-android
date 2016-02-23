package com.anu.utanglist.views

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.anu.utanglist.R
import com.anu.utanglist.models.Debt
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget

/**
 * Created by irvan on 2/20/16.
 */
class DebtItemView : RelativeLayout {

    private var imageViewPhoto: ImageView? = null
    private var textViewAmount: TextView? = null
    private var textViewLabelName: TextView? = null
    private var textViewName: TextView? = null

    var onItemClickListener: OnItemClickListener? = null

    var debt: Debt? = null
        set(value) {
            field = value

            Glide.with(context)
                    .load(value?.user?.photoUrl)
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
            if (value?.type == Debt.Type.BORROW) {
                textViewLabelName?.text = resources.getString(R.string.label_name_borrow)
            } else {
                textViewLabelName?.text = resources.getString(R.string.label_name_lend)
            }
            textViewName?.text = value?.user?.name
            textViewAmount?.text = value?.currentAmount.toString()
        }

    constructor(context: Context): super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_debt_item, this, true)

        imageViewPhoto = findViewById(R.id.photo) as ImageView
        textViewAmount = findViewById(R.id.amount) as TextView
        textViewLabelName = findViewById(R.id.labelName) as TextView
        textViewName = findViewById(R.id.name) as TextView

        setOnClickListener {
            onItemClickListener?.onItemClick(debt)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(debt: Debt?)
    }
}