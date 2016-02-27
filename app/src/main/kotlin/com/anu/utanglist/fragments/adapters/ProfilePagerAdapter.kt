package com.anu.utanglist.fragments.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.anu.utanglist.R
import com.anu.utanglist.fragments.ProfileDebtFragment
import java.util.*
import java.util.List

/**
 * Created by irvan on 2/27/16.
 */
class ProfilePagerAdapter(val context: Context, fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val fragments: List<Fragment> = ArrayList<Fragment>() as List<Fragment>

    init {
        fragments.add(ProfileDebtFragment(ProfileDebtFragment.TYPE_DEMAND))
        fragments.add(ProfileDebtFragment(ProfileDebtFragment.TYPE_OFFER))
        fragments.add(ProfileDebtFragment(ProfileDebtFragment.TYPE_DEMAND_ONGOING))
        fragments.add(ProfileDebtFragment(ProfileDebtFragment.TYPE_OFFER_ONGOING))
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val titles = context.resources.getStringArray(R.array.profile_tab_titles)
        return titles[position]
    }

    override fun getItem(position: Int): Fragment? {
        return fragments.get(position)
    }
}