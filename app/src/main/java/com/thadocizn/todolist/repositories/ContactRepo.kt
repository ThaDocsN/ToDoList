package com.thadocizn.todolist.repositories

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.widget.Toast
import com.thadocizn.todolist.data.ContactDatabase
import com.thadocizn.todolist.models.Contact
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by charles on 26,May,2019
 */
class ContactRepo(val application: Application) {

    val contactLiveData: MutableLiveData<List<Contact>> = MutableLiveData()
    private val disposable = CompositeDisposable()
    private var rowIdOfTheItemInserted: Long = 0
    private val db:ContactDatabase

    init {
        db = Room.databaseBuilder<ContactDatabase>(application.applicationContext, ContactDatabase::class.java!!, "ContactDB").build()


        disposable.add(db.contactDao.contacts
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<List<Contact>> { contacts -> contactLiveData.postValue(contacts) }, object :
                Consumer<Throwable> {
                @Throws(Exception::class)
                override fun accept(throwable: Throwable) {


                }
            }
            )
        )
    }

    fun createContact(name: String, email: String) {


        disposable.add(Completable.fromAction { rowIdOfTheItemInserted = db.contactDao.addContact(Contact(name, email,0 )) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    Toast.makeText(application.applicationContext, " contact added successfully $rowIdOfTheItemInserted", Toast.LENGTH_LONG).show()
                }

                override fun onError(e: Throwable) {

                    Toast.makeText(application.applicationContext, " error occurred ", Toast.LENGTH_LONG).show()

                }
            }))


    }

    fun updateContact(contact: Contact) {


        disposable.add(Completable.fromAction { db.contactDao.updateContact(contact) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    Toast.makeText(application.applicationContext, " contact updated successfully ", Toast.LENGTH_LONG).show()
                }

                override fun onError(e: Throwable) {

                    Toast.makeText(application.applicationContext, " error occurred ", Toast.LENGTH_LONG).show()

                }
            }))


    }


    fun deleteContact(contact: Contact?) {


        disposable.add(Completable.fromAction { contact?.let { db.contactDao.deleteContact(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    Toast.makeText(application.applicationContext, " contact deleted successfully ", Toast.LENGTH_LONG).show()
                }

                override fun onError(e: Throwable) {

                    Toast.makeText(application.applicationContext, " error occurred ", Toast.LENGTH_LONG).show()

                }
            }))
    }

    fun clear() {

        disposable.clear()
    }

}