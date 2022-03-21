package com.example.mobileapp.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileapp.R
import com.example.mobileapp.data.model.request.ProductRequest
import com.example.mobileapp.data.repository.DataStorage
import com.example.mobileapp.databinding.FragmentAddProductBinding
import com.example.mobileapp.viewmodel.MainViewModel

class AddProductFragment : DialogFragment() {

    private var binding: FragmentAddProductBinding? = null

    private lateinit var mainViewModel: MainViewModel

    companion object {
        val TAG = this::class.simpleName
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = FragmentAddProductBinding.inflate(LayoutInflater.from(context))

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setView(binding?.root).setPositiveButton(R.string.ok) { dialog, id ->
                mainViewModel.addProduct(
                    DataStorage.getToken(),
                    ProductRequest(
                        sku = binding?.edtSku?.text.toString(),
                        productName = binding?.edtName?.text.toString(),
                        qty = binding?.edtQuantity?.text.toString().toInt(),
                        price = binding?.edtPrice?.text.toString().toFloat(),
                        unit = binding?.edtUnit?.text.toString(),
                        status = binding?.edtStatus?.text.toString().toInt()
                    )
                )
            }
                .setNegativeButton(R.string.cancel) { dialog, id -> getDialog()?.cancel() }
                .setTitle(R.string.add_product)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}