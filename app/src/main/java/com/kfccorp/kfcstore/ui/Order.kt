package com.kfccorp.kfcstore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.*
import com.kfccorp.kfcstore.adapters.OrderAdapter
import com.kfccorp.kfcstore.databinding.OrderBinding
import com.kfccorp.kfcstore.interfaces.OrderClickListener
import com.kfccorp.kfcstore.models.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Order : Fragment(), OrderClickListener {

    private lateinit var binding: OrderBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    private var order: OrderItem? = Object.order
    private var isCalc = false
    private var change = 0
    private var cash = 0
    private lateinit var orderFiltered: ArrayList<OrderItemItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database
        dbRef = database.reference
        orderFiltered = arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OrderBinding.inflate(inflater, container, false)

        binding.rvOrderSummary.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }

        binding.rvOrderSummary.adapter = OrderAdapter(order!!.order!!, this)
        binding.tvTotalValue.text = order!!.total.toString()

        binding.btnCalc.setOnClickListener {
            val cash = binding.cash.text.toString().trim()
            if (cash.isNotBlank()) {
                if (cash.toInt() >= order!!.total!!) {
                    isCalc = true
                    this.cash = cash.toInt()
                    change = cash.toInt() - order!!.total!!
                    binding.change.text = change.toString()
                }
            }
        }

        binding.btnPayment.setOnClickListener {
            if (isCalc) {
                order!!.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                order!!.cash = cash
                order!!.change = change
                dbRef.child("Reports").child(order!!.timestamp!!).setValue(order)
                    .addOnSuccessListener {
                        dbRef.child("Orders").child(order!!.id!!).removeValue()
                        for (item in order!!.order!!) {
                            dbRef.child("Inventory").child(item.id!!).child("quantity")
                                .runTransaction(object : Transaction.Handler {
                                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                                        val quantity = currentData.getValue(Int::class.java)
                                        currentData.value = quantity!! - item.amount!!
                                        return Transaction.success(currentData)
                                    }
                                    override fun onComplete(
                                        error: DatabaseError?,
                                        committed: Boolean,
                                        currentData: DataSnapshot?
                                    ) {}
                                })
                        }
                    }
                findNavController().popBackStack()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onDeleteClick(id: String) {
        for (item in order!!.order!!) {
            if (item.id.equals(id))
                order!!.total = order!!.total!! - item.total!!
        }
        orderFiltered.addAll(order!!.order!!.filter { item ->
            !item.id.equals(id)
        })
        order!!.order!!.clear()
        order!!.order!!.addAll(orderFiltered.filter { true })
        binding.rvOrderSummary.adapter = OrderAdapter(order!!.order!!, this)
        binding.tvTotalValue.text = order!!.total.toString()
    }
}