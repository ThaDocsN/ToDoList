package models

import android.arch.persistence.room.Entity

/**
 * Created by charles on 26,May,2019
 */
@Entity(tableName = "contacts")
data class Contact(val name:String, val email:String, val id:Int)