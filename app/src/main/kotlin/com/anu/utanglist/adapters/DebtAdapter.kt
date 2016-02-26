package com.anu.utanglist.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.anu.utanglist.models.Debt
import com.anu.utanglist.views.DebtItemView
import java.util.*
import java.util.List

/**
 * Created by irvan on 2/20/16.
 */
class DebtAdapter : RecyclerView.Adapter<DebtAdapter.DebtItemViewHolder>() {

    val debtList: List<Debt> = ArrayList<Debt>() as List<Debt>

    var debtType: String? = null

    var onItemClickListener: DebtItemView.OnItemClickListener? = null

    override fun getItemCount(): Int {
        return debtList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DebtItemViewHolder? {
        var itemView: DebtItemView = DebtItemView(parent!!.context)
        itemView.onItemClickListener = onItemClickListener
        return DebtItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DebtItemViewHolder?, position: Int) {
        holder!!.view.debt = debtList.get(position)
        holder!!.view.debtType = debtType
    }

    class DebtItemViewHolder(val view: DebtItemView): RecyclerView.ViewHolder(view)
}