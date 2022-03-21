package com.example.mobileapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.data.model.response.ProductResponse
import com.example.mobileapp.databinding.ItemProductBinding
import com.example.mobileapp.utils.Constants
import com.example.mobileapp.utils.OnItemClick

class ProductAdapter(val isLoggedIn: Boolean, private val onClick: OnItemClick) : ListAdapter<ProductResponse, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBind(isLoggedIn, getItem(position), onClick)
    }

    override fun getItemCount(): Int = currentList.size

    fun addProduct(product: ProductResponse) {
        val updateList: MutableList<ProductResponse> = (currentList + mutableListOf(product)) as MutableList<ProductResponse>
        submitList(updateList)
        notifyDataSetChanged()
    }

    class ProductViewHolder private constructor(val binding: ItemProductBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(isLoggedIn: Boolean, product: ProductResponse, onClick: OnItemClick) {
            binding.apply {
                if (isLoggedIn) {
                    tvEdit.visibility = View.VISIBLE
                    tvDelete.visibility = View.VISIBLE
                } else {
                    tvEdit.visibility = View.GONE
                    tvDelete.visibility = View.GONE
                }
            }
            product.apply {
                sku?.let { binding.tvSku.text = it }
                productName?.let { binding.tvProductName.text = it }
            }
            binding.tvEdit.setOnClickListener {
                onClick.invoke(product, Constants.TYPE_CLICK_EDIT)
            }
            binding.tvDelete.setOnClickListener {
                onClick.invoke(product, Constants.TYPE_CLICK_DELETE)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding, parent.context)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductResponse>() {
        override fun areItemsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return oldItem == newItem
        }
    }

}