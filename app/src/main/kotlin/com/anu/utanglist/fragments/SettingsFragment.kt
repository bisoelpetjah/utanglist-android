package com.anu.utanglist.fragments

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.PreferenceFragment
import android.support.v7.app.AlertDialog
import com.anu.utanglist.R
import com.anu.utanglist.utils.PreferenceHelper
import com.anu.utanglist.utils.WebServiceHelper

/**
 * Created by irvan on 2/26/16.
 */
class SettingsFragment: PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var keyEcashId: String? = null
    private var keyEcashPin: String? = null
    private var keyLogout: String? = null

    private var prefEcashId: EditTextPreference? = null
    private var prefEcashPin: EditTextPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)

        keyEcashId = resources.getString(R.string.pref_ecash_id)
        keyEcashPin = resources.getString(R.string.pref_ecash_pin)
        keyLogout = resources.getString(R.string.pref_logout)

        prefEcashId = findPreference(keyEcashId) as EditTextPreference
        prefEcashPin = findPreference(keyEcashPin) as EditTextPreference

        prefEcashId?.summary = PreferenceHelper.ecashId
        if (PreferenceHelper.ecashPin == null) {
            prefEcashPin?.summary = ""
        } else {
            prefEcashPin?.summary = "******"
        }

        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        findPreference(keyLogout).setOnPreferenceClickListener {
            AlertDialog.Builder(context)
                    .setMessage(R.string.dialog_logout)
                    .setNegativeButton(R.string.dialog_cancel, null)
                    .setPositiveButton(R.string.dialog_ok, object: DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            WebServiceHelper.accessToken = null
                            PreferenceHelper.accessToken = null
                            activity.finish()
                        }
                    })
                    .show()

            true
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            keyEcashId -> {
                var ecashId = sharedPreferences?.getString(keyEcashId, null)
                if (ecashId.isNullOrEmpty()) ecashId = null

                PreferenceHelper.ecashId = ecashId
                prefEcashId?.summary = ecashId ?: ""
            }
            keyEcashPin -> {
                var ecashPin = sharedPreferences?.getString(keyEcashPin, null)
                if (ecashPin.isNullOrEmpty()) ecashPin = null

                PreferenceHelper.ecashPin = ecashPin
                if (ecashPin == null) {
                    prefEcashPin?.summary = ""
                } else {
                    prefEcashPin?.summary = "******"
                }
            }
        }
    }
}