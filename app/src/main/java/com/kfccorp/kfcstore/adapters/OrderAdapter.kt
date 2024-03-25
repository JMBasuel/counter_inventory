package com.kfccorp.kfcstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kfccorp.kfcstore.databinding.ItemOrdersummaryBinding
import com.kfccorp.kfcstore.interfaces.OrderClickListener
import com.kfccorp.kfcstore.models.OrderItemItem
import com.kfccorp.kfcstore.viewholders.OrderViewHolder

class OrderAdapter(
    private val order: ArrayList<OrderItemItem>,
    private val clickListener: OrderClickListener
) :
    RecyclerView.Adapter<OrderViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemOrdersummaryBinding.inflate(from, parent, false)
        return OrderViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindOrder(order[position])
    }

    override fun getItemCount(): Int = order.size

}
