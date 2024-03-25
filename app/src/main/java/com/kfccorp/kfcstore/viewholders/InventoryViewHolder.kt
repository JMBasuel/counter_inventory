package com.kfccorp.kfcstore.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kfccorp.kfcstore.databinding.ItemInventoryBinding
import com.kfccorp.kfcstore.interfaces.InventoryClickListener
import com.kfccorp.kfcstore.models.InventoryItem

class InventoryViewHolder(
    private var binding: ItemInventoryBinding,
    private val clickListener: InventoryClickListener
) :
    RecyclerView.ViewHolder(binding.root)
{
    fun bindItem(item: InventoryItem) {
        binding.itemNameTextView.text = item.product
        binding.itemPriceTextView.text = item.price.toString()
        binding.itemPriceEditText.setText(item.price.toString())
        binding.itemCategoryTextView.text = item.category
        binding.itemQuantityTextView.text = item.quantity.toString()
        binding.itemQuantityEditText.setText(item.quantity.toString())
        binding.btnEdit.setOnClickListener {
            swap2()
        }
        binding.btnDelete.setOnClickListener {
            clickListener.onDeleteClick(item.id!!)
        }
        binding.btnCancel.setOnClickListener {
            swap1()
        }
        binding.btnOK.setOnClickListener {
            clickListener.onEditConfirm(item.id!!, binding.itemPriceEditText.text.toString().toFloat(),
                binding.itemQuantityEditText.text.toString().toInt())
            swap1()
        }
    }

    private fun swap2() {
        binding.btnEdit.visibility = View.GONE
        binding.btnDelete.visibility = View.GONE
        binding.itemPriceTextView.visibility = View.GONE
        binding.itemQuantityTextView.visibility = View.GONE
        binding.btnOK.visibility = View.VISIBLE
        binding.btnCancel.visibility = View.VISIBLE
        binding.itemPriceEditText.visibility = View.VISIBLE
        binding.itemQuantityEditText.visibility = View.VISIBLE
    }

    private fun swap1() {
        binding.btnOK.visibility = View.GONE
        binding.btnCancel.visibility = View.GONE
        binding.itemPriceEditText.visibility = View.GONE
        binding.itemQuantityEditText.visibility = View.GONE
        binding.btnEdit.visibility = View.VISIBLE
        binding.btnDelete.visibility = View.VISIBLE
        binding.itemPriceTextView.visibility = View.VISIBLE
        binding.itemQuantityTextView.visibility = View.VISIBLE
    }
}