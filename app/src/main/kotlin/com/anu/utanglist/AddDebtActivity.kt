package com.anu.utanglist

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.anu.utanglist.models.Debt
import com.anu.utanglist.utils.WebServiceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by irvan on 2/20/16.
 */
class AddDebtActivity: AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var spinnerType: Spinner? = null
    private var inputLayoutAmount: TextInputLayout? = null
    private var editTextAmount: EditText? = null
    private var inputLayoutNote: TextInputLayout? = null
    private var editTextNote: EditText? = null

    private var selectedMode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debt)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        spinnerType = findViewById(R.id.type) as Spinner
        inputLayoutAmount = findViewById(R.id.amountLayout) as TextInputLayout
        editTextAmount = findViewById(R.id.amount) as EditText
        inputLayoutNote = findViewById(R.id.noteLayout) as TextInputLayout
        editTextNote = findViewById(R.id.note) as EditText

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.title_add_debt)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.debt_types, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType?.adapter = adapter

        inputLayoutAmount?.setHint(resources.getString(R.string.hint_amount))
        inputLayoutNote?.setHint(resources.getString(R.string.hint_note))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_debt, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.submit) {
            if (editTextAmount?.length() == 0 || editTextNote?.length() == 0) {
                AlertDialog.Builder(this)
                        .setMessage(R.string.error_submit)
                        .setNegativeButton(R.string.dialog_ok, null)
                        .show()
            } else {
                when (selectedMode) {
                    0 -> performAddDebtDemand(editTextAmount?.text.toString().toLong(), editTextNote?.text.toString())
                    1 -> performAddDebtOffer(editTextAmount?.text.toString().toLong(), editTextNote?.text.toString())
                }
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun performAddDebtDemand(amount: Long, note: String) {
        var progress = ProgressDialog.show(this, null, resources.getString(R.string.dialog_loading))

        WebServiceHelper.service!!.addDebtDemand(amount, note).enqueue(object: Callback<Debt> {
            override fun onResponse(call: Call<Debt>?, response: Response<Debt>?) {
                progress.cancel()
                finish()
            }

            override fun onFailure(call: Call<Debt>?, t: Throwable?) {
                progress.cancel()
                Toast.makeText(this@AddDebtActivity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun performAddDebtOffer(amount: Long, note: String) {
        var progress = ProgressDialog.show(this, null, resources.getString(R.string.dialog_loading))

        WebServiceHelper.service!!.addDebtOffer(amount, note).enqueue(object: Callback<Debt> {
            override fun onResponse(call: Call<Debt>?, response: Response<Debt>?) {
                progress.cancel()
                finish()
            }

            override fun onFailure(call: Call<Debt>?, t: Throwable?) {
                progress.cancel()
                Toast.makeText(this@AddDebtActivity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }
}