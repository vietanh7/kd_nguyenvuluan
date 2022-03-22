package com.example.mobileapp.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.mobileapp.R
import com.example.mobileapp.data.model.request.ProductRequest
import com.example.mobileapp.data.model.response.ProductResponse

class AlertDialogFragment(val context: Context) {

    var dialog: AlertDialog? = null

    private fun generateDialogFragment(id: Int): AlertDialog? = run {
        val builder = context.let { AlertDialog.Builder(it, R.style.MyAlertDialogTheme) }
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(id, null)
        builder.setView(dialogView)
        dialog?.let {
            it.dismiss()
            dialog = null
        }
        dialog = builder.create()
        dialog?.apply {
            setCancelable(true)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        }
    }

    fun showAddProductDialog(view: View, callback: (data: ProductRequest) -> Unit) {
        view.alpha = 0.5F
        dialog = generateDialogFragment(R.layout.fragment_add_product)
        dialog?.apply {
            show()
            setCancelable(false)
            val title = findViewById<AppCompatTextView>(R.id.tvTitle)
            val sku = findViewById<AppCompatEditText>(R.id.edtSku)
            val name = findViewById<AppCompatEditText>(R.id.edtName)
            val qty = findViewById<AppCompatEditText>(R.id.edtQuantity)
            val price = findViewById<AppCompatEditText>(R.id.edtPrice)
            val unit = findViewById<AppCompatEditText>(R.id.edtUnit)
            val status = findViewById<AppCompatEditText>(R.id.edtStatus)
            val cancel = findViewById<Button>(R.id.btnCancel)
            val ok = findViewById<Button>(R.id.btnOK)
            title?.text = context.resources.getString(R.string.add_product)
            cancel?.text = context.resources.getString(R.string.cancel)
            ok?.text = context.resources.getString(R.string.ok)
            cancel?.setOnClickListener {
                view.alpha = 1F
                dialog?.dismiss()
            }
            ok?.setOnClickListener {
                if (sku?.text.toString().isNotBlank() &&
                    name?.text.toString().isNotBlank() &&
                    qty?.text.toString().isNotBlank() &&
                    price?.text.toString().isNotBlank() &&
                    unit?.text.toString().isNotBlank() &&
                    status?.text.toString().isNotBlank()
                ) {
                    callback.invoke(
                        ProductRequest(
                            sku = sku?.text.toString(),
                            productName = name?.text.toString(),
                            qty = qty?.text.toString().toInt(),
                            price = price?.text.toString().toFloat(),
                            unit = unit?.text.toString(),
                            status = status?.text.toString().toInt()
                        )
                    )
                    view.alpha = 1F
                    dialog?.dismiss()
                }
            }
        }
    }

    fun showUpdateProductDialog(view: View, product: ProductResponse, callback: (data: ProductRequest) -> Unit) {
        view.alpha = 0.5F
        dialog = generateDialogFragment(R.layout.fragment_add_product)
        dialog?.apply {
            show()
            setCancelable(false)
            val title = findViewById<AppCompatTextView>(R.id.tvTitle)
            val sku = findViewById<AppCompatEditText>(R.id.edtSku)
            val name = findViewById<AppCompatEditText>(R.id.edtName)
            val qty = findViewById<AppCompatEditText>(R.id.edtQuantity)
            val price = findViewById<AppCompatEditText>(R.id.edtPrice)
            val unit = findViewById<AppCompatEditText>(R.id.edtUnit)
            val status = findViewById<AppCompatEditText>(R.id.edtStatus)
            val cancel = findViewById<Button>(R.id.btnCancel)
            val ok = findViewById<Button>(R.id.btnOK)
            title?.text = context.resources.getString(R.string.update_product)
            cancel?.text = context.resources.getString(R.string.cancel)
            ok?.text = context.resources.getString(R.string.ok)
            sku?.setText(product.sku)
            name?.setText(product.productName)
            qty?.setText(product.qty.toString())
            price?.setText(product.price.toString())
            unit?.setText(product.unit)
            status?.setText(product.status.toString())
            cancel?.setOnClickListener {
                view.alpha = 1F
                dialog?.dismiss()
            }
            ok?.setOnClickListener {
                if (sku?.text.toString().isNotBlank() &&
                    name?.text.toString().isNotBlank() &&
                    qty?.text.toString().isNotBlank() &&
                    price?.text.toString().isNotBlank() &&
                    unit?.text.toString().isNotBlank() &&
                    status?.text.toString().isNotBlank()
                ) {
                    callback.invoke(
                        ProductRequest(
                            sku = sku?.text.toString(),
                            productName = name?.text.toString(),
                            qty = qty?.text.toString().toInt(),
                            price = price?.text.toString().toFloat(),
                            unit = unit?.text.toString(),
                            status = status?.text.toString().toInt()
                        )
                    )
                    view.alpha = 1F
                    dialog?.dismiss()
                }
            }
        }
    }
}