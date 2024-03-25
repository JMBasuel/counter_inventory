package com.kfccorp.kfcstore.adapters

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.kfccorp.kfcstore.databinding.ItemInventoryBinding
import com.kfccorp.kfcstore.interfaces.InventoryClickListener
import com.kfccorp.kfcstore.models.InventoryItem
import com.kfccorp.kfcstore.viewholders.InventoryViewHolder

class InventoryAdapter(
    private val items: List<InventoryItem>,
    private val clickListener: InventoryClickListener
) :
    RecyclerView.Adapter<InventoryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInventoryBinding.inflate(inflater, parent, false)
        return InventoryViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}