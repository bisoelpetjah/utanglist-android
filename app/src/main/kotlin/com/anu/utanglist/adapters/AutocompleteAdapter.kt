package com.anu.utanglist.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.anu.utanglist.models.User
import com.anu.utanglist.views.UserSuggestionView
import java.util.*
import java.util.List

/**
 * Created by irvan on 2/20/16.
 */
class AutocompleteAdapter: RecyclerView.Adapter<AutocompleteAdapter.AutocompleteViewHolder>() {

    val userSuggestionList: List<User> = ArrayList<User>() as List<User>

    var onItemClickListener: UserSuggestionView.OnItemClickListener? = null

    override fun getItemCount(): Int {
        return userSuggestionList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AutocompleteViewHolder? {
        var itemView: UserSuggestionView = UserSuggestionView(parent!!.context)
        itemView.onItemClickListener = onItemClickListener
        return AutocompleteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AutocompleteViewHolder?, position: Int) {
        holder!!.view.user = userSuggestionList.get(position)
    }

    class AutocompleteViewHolder(val view: UserSuggestionView): RecyclerView.ViewHolder(view)
}