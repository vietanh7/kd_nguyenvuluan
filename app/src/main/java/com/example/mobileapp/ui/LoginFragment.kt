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
import com.example.mobileapp.R
import com.example.mobileapp.data.model.request.ProductRequest
import com.example.mobileapp.data.repository.DataStorage
import com.example.mobileapp.databinding.FragmentLoginBinding
import com.example.mobileapp.utils.Common
import com.example.mobileapp.utils.Common.validateEmail
import com.example.mobileapp.utils.Common.validateString
import com.example.mobileapp.utils.Constants
import com.example.mobileapp.utils.ILoadingView
import com.example.mobileapp.utils.Status
import com.example.mobileapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), ILoadingView {

    companion object {
        val TAG = this::class.simpleName
    }

    private var binding: FragmentLoginBinding? = null
    private val viewModel: MainViewModel by viewModels()

    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        onActionClick()

        return binding?.root
    }

    private fun onActionClick() {
        binding?.btnSubmit?.setOnClickListener {
            val email = binding?.edtEmail?.text.toString()
            val password = binding?.edtPassword?.text.toString()
            if (validateLogin(email, password)) doLogin(email, password)
        }
        binding?.tvSignUp?.setOnClickListener { findNavController().navigate(R.id.registerFragment) }
        binding?.icPassword?.setOnClickListener {
            binding?.edtPassword?.let { Common.togglePassword(it) }
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        if (validateString(email)) {
            onShowError(true, resources.getString(R.string.blank_email))
            return false
        }
        if (!validateEmail(email)) {
            onShowError(true, resources.getString(R.string.wrong_email))
            return false
        }
        if (validateString(password)) {
            onShowError(true, resources.getString(R.string.blank_password))
            return false
        }
        if (!validateEmail(email) || validateString(password)) {
            onShowError(true, resources.getString(R.string.validation_login))
            return false
        }
        return true
    }

    private fun doLogin(email: String, password: String) {
        viewModel.login(email, password)
        viewModel.loginResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> onShowLoading()
                Status.SUCCESS -> {
                    onHideLoading()
                    it.data?.token?.let { token ->
                        DataStorage.saveToken(token)
                        findNavController().navigate(R.id.productFragment)
                    }
                }

                Status.ERROR -> {
                    onHideLoading()
                    onShowError(true, resources.getString(R.string.wrong_login))
                }
            }
        })
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
        binding?.apply {
            if (isError) {
                tvError.text = error
                tvError.visibility = View.VISIBLE
            } else {
                tvError.text = error
                tvError.visibility = View.GONE
            }
        }
    }
}