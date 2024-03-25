package com.kfccorp.kfcstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kfccorp.kfcstore.databinding.ItemOrderitemBinding
import com.kfccorp.kfcstore.models.OrderItemItem
import com.kfccorp.kfcstore.viewholders.OrderItemViewHolder
import java.util.ArrayList

class OrderItemAdapter(
    private val order: ArrayList<OrderItemItem>
) :
    RecyclerView.Adapter<OrderItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemOrderitemBinding.inflate(from, parent, false)
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bindOrder(order[position])
    }

    override fun getItemCount(): Int = order.size

}
