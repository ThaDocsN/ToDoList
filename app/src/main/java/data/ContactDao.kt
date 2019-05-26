package data

import android.arch.persistence.room.*
import models.Contact
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

    @Query("select * from contacts")
    fun getContacts(): Flowable<List<Contact>>

    @Query("select * from contacts where id ==:contactId")
    fun getContact(contactId: Long): Contact
}