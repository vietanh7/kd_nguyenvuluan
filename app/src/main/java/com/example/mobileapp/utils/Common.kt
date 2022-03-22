package com.example.mobileapp.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

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

    fun togglePassword(edt: AppCompatEditText) {
        if (edt.transformationMethod == HideReturnsTransformationMethod.getInstance())
            edt.transformationMethod = PasswordTransformationMethod.getInstance()
        else edt.transformationMethod = HideReturnsTransformationMethod.getInstance()
    }
}