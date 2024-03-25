package com.kfccorp.kfcstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kfccorp.kfcstore.databinding.ItemReportBinding
import com.kfccorp.kfcstore.models.OrderItem
import com.kfccorp.kfcstore.viewholders.ReportViewHolder

class ReportAdapter(
    private val reports: ArrayList<OrderItem>
) :
    RecyclerView.Adapter<ReportViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemReportBinding.inflate(from, parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bindReport(reports[position])
    }

    override fun getItemCount(): Int = reports.size
}
