package adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thadocizn.todolist.MainActivity
import com.thadocizn.todolist.R
import models.Contact
import java.util.ArrayList

/**
 * Created by charles on 26,May,2019
 */
class ContactsAdapter( val context: Context,  val contactList: ArrayList<Contact>,  val mainActivity: MainActivity) : RecyclerView.Adapter<ContactsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var name: TextView = view.findViewById(R.id.name)
        var emil: TextView = view.findViewById(R.id.email)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val contact = contactList[position]

        holder.name.text = contact.name
        holder.emil.text = contact.email

        holder.itemView.setOnClickListener { mainActivity.addAndEditContacts(true, contact, position) }

    }

    override fun getItemCount() = contactList.size

}

