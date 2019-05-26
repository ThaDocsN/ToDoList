package repositories

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import data.ContactDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import models.Contact

/**
 * Created by charles on 26,May,2019
 */
class ContactRepo(application: Application) {

    private val contactLiveData: MutableLiveData<List<Contact>> = MutableLiveData()
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val db: ContactDatabase = ContactDatabase.getDatabase(application)
    private var rowIdOfTheItemInserted: Long = 0

    init {
        disposable.add(
            db.contactDao().getContacts()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contacts -> contactLiveData.postValue(contacts) },
                    { })
        )

    }

    fun createContact(name: String, email: String) {
        disposable.add(Completable.fromAction {
            rowIdOfTheItemInserted = db.contactDao().addContact(Contact(name, email, 0))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {

                }

                override fun onError(e: Throwable) {

                }
            }))
    }

    fun updateContact(contact: Contact) {
        disposable.add(Completable.fromAction { db.contactDao().updateContact(contact) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {

                }

                override fun onError(e: Throwable) {

                }
            }))
    }

    fun deleteContact(contact: Contact) {
        disposable.add(Completable.fromAction { db.contactDao().deleteContact(contact) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {

                }

                override fun onError(e: Throwable) {

                }
            }))
    }

    fun clear() {
        disposable.clear()
    }
}