package com.kfccorp.kfcstore.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.navigation.fragment.findNavController
import com.kfccorp.kfcstore.databinding.ManagementBinding

class Management : Fragment() {

    private lateinit var binding: ManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ManagementBinding.inflate(inflater, container, false)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        binding.btnReport.setOnClickListener {
            findNavController().navigate(ManagementDirections.actionManagementToReport())
        }

        binding.btnInventory.setOnClickListener {
            findNavController().navigate(ManagementDirections.actionManagementToInventory())
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}