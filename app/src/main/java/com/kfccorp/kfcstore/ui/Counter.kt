package com.kfccorp.kfcstore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.database.*
import com.kfccorp.kfcstore.databinding.CounterBinding
import com.kfccorp.kfcstore.models.*

class Counter : Fragment() {

    private lateinit var binding: CounterBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database
        dbRef = database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CounterBinding.inflate(inflater, container, false)

        binding.confirmButton.setOnClickListener{
            if (!binding.receiptNumber.text.isNullOrBlank() && binding.receiptNumber.text.toString().length == 6) {
                binding.progress.visibility = View.VISIBLE
                val orderID = binding.receiptNumber.text.toString()
                dbRef.child("Orders").child(orderID)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val order = snapshot.getValue(OrderItem::class.java)
                                Object.order = order
                                binding.progress.visibility = View.GONE
                                binding.receiptNumber.text = null
                                findNavController().navigate(CounterDirections.actionCounterToOrder())
                            } else {
                                Toast.makeText(requireContext(), "Order expired", Toast.LENGTH_SHORT).show()
                                binding.receiptNumber.text = null
                                binding.progress.visibility = View.GONE
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else binding.receiptNumber.error = "Required"
        }

        return binding.root
    }
}