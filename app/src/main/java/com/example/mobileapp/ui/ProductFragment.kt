package com.example.mobileapp.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileapp.R
import com.example.mobileapp.adapter.ProductAdapter
import com.example.mobileapp.data.model.response.ProductResponse
import com.example.mobileapp.data.repository.DataStorage.checkLogin
import com.example.mobileapp.data.repository.DataStorage.getToken
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
    private var headerToken: String? = getToken()
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var productAdapter: ProductAdapter
    private var isLoggedIn: Boolean = checkLogin()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        Log.d(TAG, "tokenString: $headerToken")

        initObserver()
        initAdapter()
        onActionClick()
        searchProduct()

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
    }

    private fun getAllProducts(token: String?) {
        val header = if (token != null) Constants.header + token else Constants.header + ""
        viewModel.getAllProducts(header)
        viewModel.getAllProductsResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> onShowLoading()
                Status.SUCCESS -> {
                    onHideLoading()
                    binding?.rvProducts?.visibility = View.VISIBLE
                    productAdapter.submitList(it.data?.toMutableList())
                }
                Status.ERROR -> {
                }
            }
        })
    }

    private fun addProduct() {
        val dialog = AlertDialogFragment(requireContext())
        binding?.mContainer?.let {
            dialog.showAddProductDialog(it) {
                viewModel.addProduct(getToken(), it)
                viewModel.addProductResponse.observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.LOADING -> onShowLoading()
                        Status.SUCCESS -> {
                            onHideLoading()
                            Log.d(TAG, "productData: ${it.data}")
                            it.data?.let { data ->
                                if (it.data.sku == null) return@let
                                productAdapter.addProduct(data)
                            }
                        }
                        Status.ERROR -> onHideLoading()
                    }
                })
            }
        }
    }

    private fun editProduct(product: ProductResponse) {
        val dialog = AlertDialogFragment(requireContext())
        binding?.mContainer?.let {
            dialog.showUpdateProductDialog(it, product) {
                viewModel.updateProduct(headerToken, it)
                viewModel.updateProductResponse.observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.LOADING -> onShowLoading()
                        Status.SUCCESS -> {
                            onHideLoading()
                            it.data?.let { data -> productAdapter.updateProduct(data) }
                        }
                        Status.ERROR -> {
                            onHideLoading()
                        }
                    }
                })
            }
        }
    }

    private fun deleteProduct(sku: String) {
        viewModel.deleteProduct(headerToken, sku)
        viewModel.deleteProductResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> onShowLoading()
                Status.SUCCESS -> {
                    onHideLoading()
                    it.data?.let { data -> productAdapter.deleteProduct(data) }
                }
                Status.ERROR -> {
                    onHideLoading()
                }
            }
        })
    }

    private fun searchProduct() {
        binding?.searchView?.queryHint = resources.getString(R.string.search)
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(search: String?): Boolean {
                if (search?.length == 0) getAllProducts(getToken())
                else {
                    search?.let {
                        viewModel.searchProduct(getToken(), it)
                        viewModel.searchProductResponse.observe(viewLifecycleOwner, {
                            when (it.status) {
                                Status.LOADING -> {
                                }
                                Status.SUCCESS -> {
                                    it.data?.let { data ->
                                        val search = mutableListOf(data)
                                        if (search.isNullOrEmpty() || search[0].sku.isNullOrEmpty()) {
                                            binding?.rvProducts?.visibility = View.GONE
                                        } else {
                                            productAdapter.submitList(search)
                                            binding?.rvProducts?.visibility = View.VISIBLE
                                        }
                                    }
                                }
                                Status.ERROR -> {
                                }
                            }
                        })
                    }
                }
                return false
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
                    editProduct(this)
                }
                Constants.TYPE_CLICK_DELETE -> {
                    sku?.let { deleteProduct(it) }
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
        binding?.cvAddProduct?.setOnClickListener {
            addProduct()
        }
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