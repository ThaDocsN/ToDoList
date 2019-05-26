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

    abstract fun contactDao():ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "Contact_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}