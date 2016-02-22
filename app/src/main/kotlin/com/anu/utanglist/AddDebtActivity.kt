package com.anu.utanglist

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.anu.utanglist.adapters.AutocompleteAdapter
import com.anu.utanglist.models.User
import com.anu.utanglist.utils.WebServiceHelper
import com.anu.utanglist.views.UserSuggestionView
import com.malinskiy.superrecyclerview.SuperRecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by irvan on 2/20/16.
 */
class AddDebtActivity: AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnFocusChangeListener, TextWatcher, UserSuggestionView.OnItemClickListener {

    private var toolbar: Toolbar? = null
    private var spinnerType: Spinner? = null
    private var inputLayoutName: TextInputLayout? = null
    private var editTextName: EditText? = null
    private var selectedUserView: UserSuggestionView? = null
    private var buttonClear: ImageButton? = null
    private var inputLayoutNote: TextInputLayout? = null
    private var editTextNote: EditText? = null
    private var recyclerViewAutocomplete: SuperRecyclerView? = null
    private var emptyAutocomplete: TextView? = null

    private val autocompleteAdapter: AutocompleteAdapter = AutocompleteAdapter()

    private var selectedUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debt)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        spinnerType = findViewById(R.id.type) as Spinner
        inputLayoutName = findViewById(R.id.nameLayout) as TextInputLayout
        editTextName = findViewById(R.id.name) as EditText
        selectedUserView = findViewById(R.id.selectedUser) as UserSuggestionView
        buttonClear = findViewById(R.id.clear) as ImageButton
        inputLayoutNote = findViewById(R.id.noteLayout) as TextInputLayout
        editTextNote = findViewById(R.id.note) as EditText
        recyclerViewAutocomplete = findViewById(R.id.autocomplete) as SuperRecyclerView
        emptyAutocomplete = findViewById(R.id.emptyAutocomplete) as TextView

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.debt_types, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType?.adapter = adapter
        spinnerType?.onItemSelectedListener = this

        inputLayoutName?.setHint(resources.getString(R.string.hint_lender))
        inputLayoutNote?.setHint(resources.getString(R.string.hint_note))

        editTextName?.onFocusChangeListener = this
        editTextName?.addTextChangedListener(this)

        buttonClear?.setOnClickListener {
            selectedUser = null

            inputLayoutName?.visibility = View.VISIBLE
            selectedUserView?.visibility = View.GONE

            editTextName?.text!!.clear()
            editTextName?.requestFocus()
        }

        autocompleteAdapter.onItemClickListener = this
        recyclerViewAutocomplete?.adapter = autocompleteAdapter
        recyclerViewAutocomplete?.recyclerView?.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        when (pos) {
            0 -> {
                inputLayoutName?.setHint(resources.getString(R.string.hint_lender))
            }
            1 -> {
                inputLayoutName?.setHint(resources.getString(R.string.hint_borrower))
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus && editTextName?.length()!! >= 2) {
            recyclerViewAutocomplete?.visibility = View.VISIBLE
        } else {
            recyclerViewAutocomplete?.visibility = View.GONE
            emptyAutocomplete?.visibility = View.GONE
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (s?.length!! < 2) {
            recyclerViewAutocomplete?.visibility = View.GONE
            emptyAutocomplete?.visibility = View.GONE
        } else {
            recyclerViewAutocomplete?.visibility = View.VISIBLE
            performGetUserSuggestions(s.toString())
        }
    }

    override fun onItemClick(user: User?) {
        selectedUser = user
        selectedUserView?.user = selectedUser

        recyclerViewAutocomplete?.visibility = View.GONE
        inputLayoutName?.visibility = View.GONE
        selectedUserView?.visibility = View.VISIBLE
    }

    private fun performGetUserSuggestions(name: String) {
        WebServiceHelper.service!!.getUserSuggestion(name).enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                autocompleteAdapter.userSuggestionList.clear()
                autocompleteAdapter.userSuggestionList.addAll(0, response?.body())
                autocompleteAdapter.notifyDataSetChanged()

                if (autocompleteAdapter.itemCount == 0) {
                    emptyAutocomplete?.visibility = View.VISIBLE
                } else {
                    emptyAutocomplete?.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                Toast.makeText(this@AddDebtActivity, R.string.error_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }
}