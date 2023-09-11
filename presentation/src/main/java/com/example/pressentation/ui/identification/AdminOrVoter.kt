package com.example.pressentation.ui.identification

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.pressentation.R
import com.example.domain.utils.ConstantFragmentsDeepLinkUri
import com.example.domain.utils.Constants
import com.example.domain.utils.Resourse
import com.example.pressentation.databinding.FragmentAdminOrVoterBinding
import com.example.pressentation.sharedViewModel.GetTimeViewModel
import com.example.pressentation.sharedViewModel.TokenManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminOrVoter : Fragment(R.layout.fragment_admin_or_voter) {

    lateinit var binding: FragmentAdminOrVoterBinding
    private val token_view_model: TokenManagerViewModel by viewModels()
    private val view_model: GetTimeViewModel by viewModels()
    private var token: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminOrVoterBinding.bind(view)

        binding.adminLayout.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri(ConstantFragmentsDeepLinkUri.LoginAdmin.toUri())
                .build()
            findNavController().navigate(request)
        }

        lifecycleScope.launchWhenCreated {

            val resourse = token_view_model.getVoterToken()
            when (resourse) {
                is Resourse.Loading -> {}
                is Resourse.Success -> token = resourse.data!!
                is Resourse.Error -> Log.d("saeed", "${resourse.message} error ")
            }
        }

        lifecycleScope.launchWhenCreated {
            view_model.get_time_state_flow.collect { resourse ->
                when (resourse) {
                    is Resourse.Loading -> {}
                    is Resourse.Success -> {
                        binding.userLayout.setOnClickListener {
                            if (resourse.data!!.end_at.isNotEmpty())
                                handNavigateDependOnToken()
                            else
                                Toast.makeText(requireContext(), "time not begin to vote", Toast.LENGTH_LONG).show()
                        }
                    }
                    is Resourse.Error -> {
                        Log.d("saeed", "${resourse.message} error ")
                    }
                }
            }
        }
    }

    private fun handNavigateDependOnToken() {
        if (token.isNotEmpty()) {
            if (token == Constants.empty_voter) {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(ConstantFragmentsDeepLinkUri.LoginVoter.toUri())
                    .build()
                findNavController().navigate(request)
            } else {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(ConstantFragmentsDeepLinkUri.Candidates.toUri())
                    .build()
                findNavController().navigate(request)
            }

        }
    }
}