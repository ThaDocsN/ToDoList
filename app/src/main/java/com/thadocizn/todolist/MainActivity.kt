package com.thadocizn.todolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.thadocizn.todolist.models.Contact
import com.thadocizn.todolist.utils.ContactsAdapter
import com.thadocizn.todolist.viewModels.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ContactViewModel
    val list = ArrayList<Contact>()
    val adapter = ContactsAdapter(this,list, this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        getRecycleViewer()
        viewModel.allContacts.observe(this, Observer{contacts ->
            list.clear()
            list.addAll(contacts!!)
            adapter.notifyDataSetChanged()
        })
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val countries = listOf("Russia", "USA", "Japan", "Australia")
            selector("Where are you from?", countries) { _, i ->
                toast("So you're living in ${countries[i]}, right?")
            }
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

    fun addAndEditContacts(b: Boolean, contact: Contact, position: Int) {

    }
}
