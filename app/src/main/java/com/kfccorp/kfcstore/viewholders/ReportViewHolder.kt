package com.kfccorp.kfcstore.viewholders

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kfccorp.kfcstore.adapters.OrderItemAdapter
import com.kfccorp.kfcstore.databinding.ItemReportBinding
import com.kfccorp.kfcstore.models.OrderItem

@SuppressLint("SetTextI18n")
class ReportViewHolder(
    private var binding: ItemReportBinding
) :
    RecyclerView.ViewHolder(binding.root)
{
    fun bindReport(report: OrderItem) {
        binding.tvTimeStamp.text = "Date: ${report.timestamp}"
        binding.tvTotal.text = "Total: ${report.total}"
        binding.tvCash.text = "Cash: ${report.cash}"
        binding.tvChange.text = "Change: ${report.change}"
        binding.rvOrderItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
        binding.rvOrderItems.adapter = OrderItemAdapter(report.order!!)
    }
}
