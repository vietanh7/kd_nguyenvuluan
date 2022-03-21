package com.example.mobileapp.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.util.Patterns
import android.view.KeyEvent
import android.widget.Toast

object Common {

    fun getProgressDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.progress = ProgressDialog.STYLE_SPINNER
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setOnKeyListener { dialog: DialogInterface?, keyCode: Int, event: KeyEvent? -> true }
        return progressDialog
    }

    fun showToast(context: Context?, message: String?): Toast? {
        return Toast.makeText(context, message, Toast.LENGTH_SHORT)
    }

    fun validateEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validateString(string: String): Boolean {
        return TextUtils.isEmpty(string)
    }
}