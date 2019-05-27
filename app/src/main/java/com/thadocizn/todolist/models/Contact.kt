package com.thadocizn.todolist.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by charles on 26,May,2019
 */
@Entity(tableName = "contacts")
data class Contact(val name:String, val email:String, @PrimaryKey(autoGenerate = true)val id:Long)