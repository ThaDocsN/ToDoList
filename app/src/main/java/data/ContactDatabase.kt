package data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import models.Contact

/**
 * Created by charles on 26,May,2019
 */
@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase:RoomDatabase() {

    abstract val contactDao:ContactDao

}