package com.thadocizn.todolist.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.thadocizn.todolist.models.Contact

/**
 * Created by charles on 26,May,2019
 */
@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase:RoomDatabase() {

    abstract val contactDao:ContactDao

}