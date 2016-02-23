package com.anu.utanglist.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.activeandroid.ActiveAndroid
import com.activeandroid.query.Select
import com.anu.utanglist.R
import com.anu.utanglist.adapters.DebtAdapter
import com.anu.utanglist.models.Debt
import com.anu.utanglist.utils.WebServiceHelper
import com.anu.utanglist.views.DebtItemView
import com.malinskiy.superrecyclerview.SuperRecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by irvan on 2/20/16.
 */
class BorrowFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener, DebtItemView.OnItemClickListener {

    private var recyclerViewUtang: SuperRecyclerView? = null
    private var emptyDebt: TextView? = null

    private val debtAdapter: DebtAdapter = DebtAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater?.inflate(R.layout.fragment_debt, container, false)

        recyclerViewUtang = view?.findViewById(R.id.debt) as SuperRecyclerView
        emptyDebt = view?.findViewById(R.id.emptyDebt) as TextView

        recyclerViewUtang?.adapter = debtAdapter
        recyclerViewUtang?.setLayoutManager(LinearLayoutManager(context))
        recyclerViewUtang?.setRefreshListener(this)

        return view
    }

    override fun onStart() {
        super.onStart()

        performGetMoneyBorrowedList()
    }

    override fun onRefresh() {
        recyclerViewUtang?.setRefreshing(true)

        performGetMoneyBorrowedList()
    }

    override fun onItemClick(debt: Debt?) {}

    private fun performGetMoneyBorrowedList() {
        WebServiceHelper.service!!.getMoneyBorrowedList().enqueue(object: Callback<List<Debt>> {
            override fun onResponse(call: Call<List<Debt>>?, response: Response<List<Debt>>?) {
                ActiveAndroid.beginTransaction()
                try {
                    response?.body()?.forEach {
                        it.type = Debt.Type.BORROW
                        it.save()
                    }
                    ActiveAndroid.setTransactionSuccessful()
                } finally {
                    ActiveAndroid.endTransaction()
                }

                debtAdapter.debtList.clear()
                debtAdapter.debtList.addAll(0, response?.body())
                debtAdapter.notifyDataSetChanged()

                recyclerViewUtang?.setRefreshing(false)

                if (debtAdapter.itemCount == 0) {
                    emptyDebt?.visibility = View.VISIBLE
                } else {
                    emptyDebt?.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<List<Debt>>?, t: Throwable?) {
                val debts = Select().from(Debt::class.java).where("type = ?", Debt.Type.BORROW).execute<Debt>()

                debtAdapter.debtList.clear()
                debtAdapter.debtList.addAll(0, debts)
                debtAdapter.notifyDataSetChanged()

                recyclerViewUtang?.setRefreshing(false)

                Toast.makeText(activity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }
}