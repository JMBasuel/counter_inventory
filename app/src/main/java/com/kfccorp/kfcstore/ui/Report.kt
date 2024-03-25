package com.kfccorp.kfcstore.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.*
import com.kfccorp.kfcstore.adapters.ReportAdapter
import com.kfccorp.kfcstore.databinding.ReportBinding
import com.kfccorp.kfcstore.models.OrderItem

@SuppressLint("ClickableViewAccessibility")
class Report : Fragment() {

    private lateinit var binding: ReportBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    private lateinit var reports: ArrayList<OrderItem>
    private lateinit var filtered: ArrayList<OrderItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database
        dbRef = database.reference
        reports = arrayListOf()
        filtered = arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReportBinding.inflate(inflater, container, false)

        binding.root.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    binding.root.clearFocus()
                }
            }
            true
        }

        binding.root.setOnFocusChangeListener { _, isFocused ->
            if (!isFocused) hideKeyboard()
        }

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        binding.rvOrderReport.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }

        dbRef.child("Reports").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                reports.clear()
                if (snapshot.exists()) {
                    for (report in snapshot.children)
                        reports.add(report.getValue(OrderItem::class.java)!!)
                    reports.sortWith(compareByDescending { it.timestamp })
                    binding.rvOrderReport.adapter = ReportAdapter(reports)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchBar.setOnQueryTextListener(searchListener())

        binding.btnSeeAll.setOnClickListener {
            if (!binding.searchBar.query.isNullOrBlank())
                binding.searchBar.setQuery(null, false)
        }

        return binding.root
    }

    private fun searchListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.root.clearFocus()
                filterDataOnSubmit(query)
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                filterDataOnClear(query)
                return true
            }
        }
    }

    private fun filterDataOnClear(query: String?) {
        if (query.isNullOrBlank())
            binding.rvOrderReport.adapter = ReportAdapter(reports)
    }

    private fun filterDataOnSubmit(query: String?) {
        filtered.clear()
        if (!query.isNullOrBlank()) {
            filtered.addAll(reports.filter { item ->
                item.timestamp!!.contains(query, true)
            })
            binding.rvOrderReport.adapter = ReportAdapter(filtered)
        }
    }

    private fun hideKeyboard() {
        val imm = requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus
        if (view != null) imm.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}