package com.thadocizn.todolist.data

import android.arch.persistence.room.*
import com.thadocizn.todolist.models.Contact
import io.reactivex.Flowable


/**
 * Created by charles on 26,May,2019
 */
@Dao
interface ContactDao {
    @Insert
    fun addContact(contact: Contact): Long

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @get:Query("select * from contacts")
    val contacts: Flowable<List<Contact>>

    @Query("select * from contacts where id ==:contactId")
    fun getContact(contactId: Long): Contact
}