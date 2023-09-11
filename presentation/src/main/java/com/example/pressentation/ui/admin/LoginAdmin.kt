package com.example.pressentation.ui.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.pressentation.R
import com.example.pressentation.databinding.FragmentLoginAdminBinding
import com.example.domain.utils.ConstantFragmentsDeepLinkUri
import com.example.domain.utils.Resourse
import com.example.pressentation.sharedViewModel.TokenManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginAdmin : Fragment(R.layout.fragment_login_admin) {

    private val view_model: AdminViewModel by viewModels()
    private val token_view_model: TokenManagerViewModel by viewModels()
    lateinit var binding: FragmentLoginAdminBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginAdminBinding.bind(view)

        lifecycleScope.launchWhenCreated {
            view_model.login_admin_state_flow.collect {

                when (it) {
                    is Resourse.Loading ->
                        binding.loginAdminProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.loginAdminProgressPar.visibility = View.GONE

                        handleSaveToken(it.data!!) {
                            findNavController().popBackStack()
                            val request = NavDeepLinkRequest.Builder
                                .fromUri(ConstantFragmentsDeepLinkUri.AdminAuthurization.toUri())
                                .build()
                            findNavController().navigate(request)
                        }

                    }
                    is Resourse.Error -> {
                        Log.d("saeed", "${it.message} ")
                        binding.loginAdminProgressPar.visibility = View.GONE
                    }
                }
            }
        }

        binding.loginAdminButton.setOnClickListener {


            val email = getStringFromEditText(binding.loginAdminEditEmail)
            val password = getStringFromEditText(binding.loginAdminEditPassward)
            if (checkNotEmpty(email, password))
                view_model.loginAdmin(email, password)
            else
                Toast.makeText(requireContext(), "email or password is empty", Toast.LENGTH_LONG)
                    .show()
        }

    }

    private fun checkNotEmpty(email: String, password: String): Boolean =
        email.isNotEmpty() && password.isNotEmpty()

    private fun getStringFromEditText(edit_text: EditText): String = edit_text.text.toString()

    private suspend fun handleSaveToken(token: String, sucess_handle: () -> Unit) {

        val resourse = token_view_model.setAdminToken(token)
        when (resourse) {
            is Resourse.Loading -> {}
            is Resourse.Success -> sucess_handle()
            is Resourse.Error -> {
                Log.d("saeed", "${resourse.message} error ")
            }
        }
    }

}