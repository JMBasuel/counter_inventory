package com.kfccorp.kfcstore.popups

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.kfccorp.kfcstore.databinding.DialogBinding

class Dialog(
    private val callback: () -> Unit
) :
    DialogFragment()
{
    private lateinit var binding: DialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)

        binding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnYes.setOnClickListener {
            callback()
            dialog.dismiss()
        }

        if (dialog.window != null) dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        return dialog
    }
}