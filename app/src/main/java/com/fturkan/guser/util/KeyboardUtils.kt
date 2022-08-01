package com.fturkan.guser.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

class KeyboardUtils {

    companion object {

        fun showKeyboard(context: Context, view: View){
            if (view.requestFocus()) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.showSoftInput(view, 0)
            }
        }

        fun hideKeyboard(context: Context, view: View){
            val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }

}