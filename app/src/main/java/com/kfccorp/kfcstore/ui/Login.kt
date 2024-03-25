package com.kfccorp.kfcstore.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.kfccorp.kfcstore.databinding.LoginBinding

@SuppressLint("SetTextI18n")
class Login : Fragment() {

    private lateinit var binding: LoginBinding
    private val username = "admin"
    private val password = "admin"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginBinding.inflate(inflater, container, false)

        binding.spnLevel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent?.getItemAtPosition(position).toString() == "EMPLOYEE") {
                    hideKeyboard()
                    binding.edtUsername.setText("")
                    binding.edtPassword.setText("")
                    binding.edtUsername.visibility = View.GONE
                    binding.edtPassword.visibility = View.GONE
                } else {
                    binding.edtUsername.visibility = View.VISIBLE
                    binding.edtPassword.visibility = View.VISIBLE
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.btnLogin.setOnClickListener {
            if (binding.spnLevel.selectedItem.toString() == "MANAGER") {
                if (binding.edtPassword.text.toString() == "") {
                    binding.edtPassword.requestFocus()
                    binding.tvPassError.text = "Missing password"
                } else {
                    binding.tvPassError.text = ""
                    if (binding.edtPassword.text.toString() != password) {
                        binding.edtPassword.setText("")
                        binding.edtPassword.requestFocus()
                        binding.tvPassError.text = "Incorrect password"
                    } else {
                        binding.tvPassError.text = ""
                    }
                }
                if (binding.edtUsername.text.toString() == "") {
                    binding.edtUsername.requestFocus()
                    binding.tvUserError.text = "Missing email"
                } else {
                    binding.tvUserError.text = ""
                    if (binding.edtUsername.text.toString() != username) {
                        binding.edtUsername.setText("")
                        binding.edtUsername.requestFocus()
                        binding.tvUserError.text = "Incorrect email"
                    } else {
                        binding.tvUserError.text = ""
                    }
                }
                if (binding.edtUsername.text.toString() != "" && binding.edtPassword.text.toString() != "") {
                    binding.tvUserError.text = ""
                    binding.tvPassError.text = ""
                    binding.tvUserError.text = ""
                    if (binding.edtUsername.text.toString() == username && binding.edtPassword.text.toString() == password) {
                        binding.tvUserError.text = ""
                        binding.tvPassError.text = ""
                        hideKeyboard()
                        binding.edtUsername.text = null
                        binding.edtPassword.text = null
                        binding.edtUsername.clearFocus()
                        binding.edtPassword.clearFocus()
                        findNavController().navigate(LoginDirections.actionLoginToManagement())
                    }
                }
            } else {
                findNavController().navigate(LoginDirections.actionLoginToCounter())
            }
        }

        return binding.root
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS) }
    }
}