package com.kfccorp.kfcstore.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.kfccorp.kfcstore.databinding.ItemOrdersummaryBinding
import com.kfccorp.kfcstore.interfaces.OrderClickListener
import com.kfccorp.kfcstore.models.OrderItemItem

class OrderViewHolder(
    private var binding: ItemOrdersummaryBinding,
    private val clickListener: OrderClickListener
) :
    RecyclerView.ViewHolder(binding.root)
{
    fun bindOrder(orderItem: OrderItemItem) {
        binding.tvItemName.text = orderItem.name
        var customs = ""
        when(orderItem.customs) {
            is ArrayList<*> -> {
                for (custom in orderItem.customs)
                    if (custom != null) customs += "$custom\n"
            }
            is HashMap<*, *> -> {
                for ((_, custom) in orderItem.customs)
                    if (custom != null) customs += "$custom\n"
            }
        }
        binding.customs.text = customs
        binding.tvItemPrice.text = orderItem.price.toString()
        binding.tvQuantity.text = orderItem.amount.toString()
        binding.tvTotalPrice.text = orderItem.total.toString()
        binding.btnDelete.setOnClickListener {
            clickListener.onDeleteClick(orderItem.id!!)
        }
    }

}
