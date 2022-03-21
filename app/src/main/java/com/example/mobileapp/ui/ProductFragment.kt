package com.example.mobileapp.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileapp.R
import com.example.mobileapp.adapter.ProductAdapter
import com.example.mobileapp.data.model.response.ProductResponse
import com.example.mobileapp.data.repository.DataStorage
import com.example.mobileapp.databinding.FragmentProductBinding
import com.example.mobileapp.utils.Common
import com.example.mobileapp.utils.Constants
import com.example.mobileapp.utils.ILoadingView
import com.example.mobileapp.utils.Status
import com.example.mobileapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment(), ILoadingView {

    companion object {
        val TAG = this::class.simpleName
    }

    private var binding: FragmentProductBinding? = null
    private val viewModel: MainViewModel by viewModels()
    private var headerToken: String = DataStorage.getToken()
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var productAdapter: ProductAdapter
    private var isLoggedIn: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        Log.d(TAG, "tokenString: $headerToken")
        isLoggedIn = headerToken != ""

        initObserver()
        initAdapter()
        onActionClick()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObserver() {
        if (isLoggedIn) {
            binding?.cvAddProduct?.visibility = View.VISIBLE
            binding?.tvRegister?.visibility = View.GONE
            binding?.tvLogin?.visibility = View.GONE
        } else {
            binding?.cvAddProduct?.visibility = View.GONE
            binding?.tvRegister?.visibility = View.VISIBLE
            binding?.tvLogin?.visibility = View.VISIBLE
        }
        getAllProducts(headerToken)
        addProduct()
    }

    private fun getAllProducts(token: String?) {
        val header = Constants.header + token
        viewModel.getAllProducts(header)
        viewModel.getAllProductsResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> onShowLoading()
                Status.SUCCESS -> {
                    onHideLoading()
                    productAdapter.submitList(it.data?.toMutableList())
                }
                Status.ERROR -> {
                }
            }
        })
    }
    private fun addProduct() {
        viewModel.addProductResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> onShowLoading()
                Status.SUCCESS -> {
                    onHideLoading()
                    it.data?.let { data -> productAdapter.addProduct(data) }
                }
                Status.ERROR -> {
                }
            }
        })
    }

    private fun initAdapter() {
        with(binding?.rvProducts) {
            productAdapter =
                ProductAdapter(isLoggedIn) { callback, action -> onActionClick(callback, action) }
            this?.adapter = productAdapter
            this?.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onActionClick(data: ProductResponse?, action: Int) {
        data?.apply {
            when (action) {
                Constants.TYPE_CLICK_EDIT -> {

                }
                Constants.TYPE_CLICK_DELETE -> {
                    
                }
            }
        }
    }

    private fun onActionClick() {
        binding?.tvLogin?.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding?.tvRegister?.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        binding?.cvAddProduct?.setOnClickListener { AddProductFragment().show(childFragmentManager, AddProductFragment.TAG) }
    }

    override fun onShowLoading() {
        binding?.apply {
            mContainer.alpha = 0.5F
            mProgressDialog = Common.getProgressDialog(requireContext())
            mProgressDialog.setMessage(getString(R.string.loading))
            mProgressDialog.show()
        }
    }

    override fun onHideLoading() {
        binding?.apply {
            mContainer.alpha = 1F
            if (mProgressDialog.isShowing) {
                mProgressDialog.dismiss()
            }
        }
    }

    override fun onShowError(isError: Boolean, error: String) {

    }

}