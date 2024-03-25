package com.kfccorp.kfcstore.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.*
import com.kfccorp.kfcstore.adapters.InventoryAdapter
import com.kfccorp.kfcstore.databinding.InventoryBinding
import com.kfccorp.kfcstore.interfaces.InventoryClickListener
import com.kfccorp.kfcstore.models.InventoryItem
import com.kfccorp.kfcstore.popups.Dialog

class Inventory : Fragment(), InventoryClickListener {

    private lateinit var binding: InventoryBinding
    private lateinit var items: ArrayList<InventoryItem>
    private lateinit var adapter: InventoryAdapter
    private lateinit var dialog: Dialog
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
        binding = InventoryBinding.inflate(inflater, container, false)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        items = ArrayList()
        binding.recyclerViewInventory.layoutManager = LinearLayoutManager(context)
        getInventory()

        return binding.root
    }

    private fun getInventory() {
        dbRef.child("temp_Inventory")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    items.clear()
                    if (snapshot.exists()) {
                        for (itemSnapshot in snapshot.children) {
                            items.add(itemSnapshot.getValue(InventoryItem::class.java)!!)
                        }
                    }
                    adapter = InventoryAdapter(items, this@Inventory)
                    binding.recyclerViewInventory.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onEditConfirm(id: String, price: Float, quantity: Int) {
        val update = mapOf<String, Any>("price" to price, "quantity" to quantity)
        dbRef.child("temp_Inventory").child(id).updateChildren(update)
    }

    override fun onDeleteClick(id: String) {
        dialog = Dialog {
            dbRef.child("temp_Inventory").child(id).removeValue()
        }
        dialog.show(childFragmentManager, "ConfirmDialog")
    }
}