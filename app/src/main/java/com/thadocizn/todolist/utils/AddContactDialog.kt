package com.thadocizn.todolist.utils

import android.content.DialogInterface
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.ViewManager
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.design.textInputLayout

/**
 * Created by charles on 27,May,2019
 */
class AddContactDialog(ui:AnkoContext<View>) {

    lateinit var dialog: DialogInterface
    lateinit var feedbackText: TextInputEditText
    lateinit var cancelButton:TextView
    lateinit var okButton:TextView

    init {
        with(ui){
            dialog = alert {
                customView{
                    verticalLayout{
                        textView(){}
                        textView(){}
                        textInputLayout(){}
                    }
                }
            }.show()
        }
    }
}
inline fun ViewManager.textInputEditText(theme:Int = 0, init:TextInputEditText.() -> Unit) =
        ankoView({TextInputEditText(it)}, theme, init)
inline fun ViewManager.textInputLayout(theme:Int = 0, init:TextInputLayout.() -> Unit) =
        ankoView({TextInputLayout(it)}, theme, init)