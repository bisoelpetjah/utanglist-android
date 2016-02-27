package com.anu.utanglist.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.anu.utanglist.DebtDetailActivity
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
class ProfileDebtFragment(val type: String) : Fragment(), DebtItemView.OnItemClickListener {

    companion object {
        final val TYPE_DEMAND = "TYPE_DEMAND"
        final val TYPE_OFFER = "TYPE_OFFER"
        final val TYPE_DEMAND_ONGOING = "TYPE_DEMAND_ONGOING"
        final val TYPE_OFFER_ONGOING = "TYPE_OFFER_ONGOING"
    }

    private var recyclerViewUtang: SuperRecyclerView? = null
    private var emptyDebt: TextView? = null

    private val debtAdapter: DebtAdapter = DebtAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater?.inflate(R.layout.fragment_debt, container, false)

        recyclerViewUtang = view?.findViewById(R.id.debt) as SuperRecyclerView
        emptyDebt = view?.findViewById(R.id.emptyDebt) as TextView

        debtAdapter.onItemClickListener = this
        recyclerViewUtang?.adapter = debtAdapter
        recyclerViewUtang?.setLayoutManager(LinearLayoutManager(context))

        return view
    }

    override fun onStart() {
        super.onStart()

        when (type) {
            TYPE_DEMAND -> {
                performGetDebtDemandList()
            }
            TYPE_OFFER -> {
                performGetDebtOfferList()
            }
            TYPE_DEMAND_ONGOING -> {
                performGetOngoingDebtDemandList()
            }
            TYPE_OFFER_ONGOING -> {
                performGetOngoingDebtOfferList()
            }
        }
    }

    override fun onItemClick(debt: Debt?) {
        val intent = Intent(context, DebtDetailActivity::class.java)
        intent.putExtra(DebtDetailActivity.EXTRA_DEBT_ID, debt?.id)
        intent.putExtra(DebtDetailActivity.EXTRA_DEBT_TYPE, Debt.TYPE_DEMAND)
        startActivity(intent)
    }

    private fun performGetDebtDemandList() {
        WebServiceHelper.service!!.getCurrentUserDebtDemandList().enqueue(object: Callback<List<Debt>> {
            override fun onResponse(call: Call<List<Debt>>?, response: Response<List<Debt>>?) {
                recyclerViewUtang?.setRefreshing(false)

                if (response?.isSuccess!!) showData(response?.body())
            }

            override fun onFailure(call: Call<List<Debt>>?, t: Throwable?) {
                recyclerViewUtang?.setRefreshing(false)

                Toast.makeText(activity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun performGetDebtOfferList() {
        WebServiceHelper.service!!.getCurrentUserDebtOfferList().enqueue(object: Callback<List<Debt>> {
            override fun onResponse(call: Call<List<Debt>>?, response: Response<List<Debt>>?) {
                recyclerViewUtang?.setRefreshing(false)

                if (response?.isSuccess!!) showData(response?.body())
            }

            override fun onFailure(call: Call<List<Debt>>?, t: Throwable?) {
                recyclerViewUtang?.setRefreshing(false)

                Toast.makeText(activity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun performGetOngoingDebtDemandList() {
        WebServiceHelper.service!!.getCurrentUserOngoingDebtDemandList().enqueue(object: Callback<List<Debt>> {
            override fun onResponse(call: Call<List<Debt>>?, response: Response<List<Debt>>?) {
                recyclerViewUtang?.setRefreshing(false)

                if (response?.isSuccess!!) showData(response?.body())
            }

            override fun onFailure(call: Call<List<Debt>>?, t: Throwable?) {
                recyclerViewUtang?.setRefreshing(false)

                Toast.makeText(activity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun performGetOngoingDebtOfferList() {
        WebServiceHelper.service!!.getCurrentUserOngoingDebtOfferList().enqueue(object: Callback<List<Debt>> {
            override fun onResponse(call: Call<List<Debt>>?, response: Response<List<Debt>>?) {
                recyclerViewUtang?.setRefreshing(false)

                if (response?.isSuccess!!) showData(response?.body())
            }

            override fun onFailure(call: Call<List<Debt>>?, t: Throwable?) {
                recyclerViewUtang?.setRefreshing(false)

                Toast.makeText(activity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showData(debtList: List<Debt>?) {
        debtAdapter.debtList.clear()
        debtAdapter.debtList.addAll(0, debtList)
        debtAdapter.notifyDataSetChanged()

        if (debtAdapter.itemCount == 0) {
            emptyDebt?.visibility = View.VISIBLE
        } else {
            emptyDebt?.visibility = View.GONE
        }
    }
}