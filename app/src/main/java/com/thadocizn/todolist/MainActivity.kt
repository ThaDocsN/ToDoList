package com.thadocizn.todolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.thadocizn.todolist.models.Contact
import com.thadocizn.todolist.utils.ContactsAdapter
import com.thadocizn.todolist.viewModels.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.layout_add_contact.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactViewModel
    private val list = ArrayList<Contact>()
    private val adapter = ContactsAdapter(this, list, this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        getRecycleViewer()
        viewModel.allContacts.observe(this, Observer { contacts ->
            list.clear()
            list.addAll(contacts!!)
            adapter.notifyDataSetChanged()
        })
        fab.setOnClickListener { view ->
            addAndEditContacts(false, null, -1)

        }
    }

    private fun getRecycleViewer() {

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addAndEditContacts(isUpdated: Boolean, contact: Contact?, position: Int) {

        val layoutInflater = LayoutInflater.from(applicationContext)
        val view = layoutInflater.inflate(R.layout.layout_add_contact, null)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(view)

        //new_contact_title.text = "Add New Contact"

        if (isUpdated ) {
            name.setText(contact?.name)
            email.setText(contact?.email)
        }

        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton(if (isUpdated) "Update" else "Save") { dialogbox, id -> }
            .setNegativeButton("Delete") { dialogBox, id ->
                if (isUpdated) {
                    contact?.let { deleteContact(it, position) }
                } else {
                    dialogBox.cancel()
                }
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (TextUtils.isEmpty(new_contact_title.text.toString())) {
                toast("Enter contact name")
                return@setOnClickListener
            } else {
                alertDialog.dismiss()
            }
            if (isUpdated && contact != null) {

                updateContact(name.text.toString(), email.text.toString(), position)
            } else {

                createContact(name.text.toString(), email.text.toString())
            }

        }
    }

    private fun deleteContact(contact: Contact, position: Int) {
        viewModel.deleteContact(contact)
    }

    private fun createContact(name: String, email: String) {
        viewModel.createContact(name, email)
    }

    private fun updateContact(name: String, email: String, position: Int) {
        val contact = list[position]
        contact.name = name
        contact.email = email
        viewModel.updateContact(contact)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }
}
