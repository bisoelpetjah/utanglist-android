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
import com.anu.utanglist.models.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget

/**
 * Created by irvan on 2/20/16.
 */
class UserSuggestionView: RelativeLayout {

    private var imageViewPhoto: ImageView? = null
    private var textViewName: TextView? = null

    var onItemClickListener: OnItemClickListener? = null

    var user: User? = null
        set(value) {
            field = value

            Glide.with(context)
                    .load(value?.photoUrl)
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
            textViewName?.text = value?.name
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
        LayoutInflater.from(context).inflate(R.layout.layout_user_suggestion, this, true)

        imageViewPhoto = findViewById(R.id.photo) as ImageView
        textViewName = findViewById(R.id.name) as TextView

        setOnClickListener {
            onItemClickListener?.onItemClick(user)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(user: User?)
    }
}