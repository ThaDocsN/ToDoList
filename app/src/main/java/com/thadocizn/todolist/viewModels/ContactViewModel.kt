package com.thadocizn.todolist.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.thadocizn.todolist.models.Contact
import com.thadocizn.todolist.repositories.ContactRepo

/**
 * Created by charles on 26,May,2019
 */
class ContactViewModel(application: Application):AndroidViewModel(application) {

    private val contactRepository: ContactRepo = ContactRepo(application)

    val allContacts: LiveData<List<Contact>>
        get() = contactRepository.contactLiveData

    fun createContact(name: String, email: String) {

        contactRepository.createContact(name, email)
    }

    fun updateContact(contact: Contact) {

        contactRepository.updateContact(contact)
    }

    fun deleteContact(contact: Contact) {

        contactRepository.deleteContact(contact)
    }

    fun clear() {

        contactRepository.clear()
    }
}