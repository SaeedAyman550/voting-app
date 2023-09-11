package com.example.pressentation.ui.identification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.pressentation.R
import com.example.domain.utils.ConstantFragmentsDeepLinkUri
import com.example.pressentation.databinding.FragmentAdminAthurizationBinding


class AdminAuthorization : Fragment(R.layout.fragment_admin_athurization) {

    lateinit var binding:FragmentAdminAthurizationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentAdminAthurizationBinding.bind(view)


        binding.candidateLayout.setOnClickListener{

            val request = NavDeepLinkRequest.Builder
                .fromUri(ConstantFragmentsDeepLinkUri.CreateCandidate.toUri())

                .build()
            findNavController().navigate(request)

        }

        binding.voterLayout.setOnClickListener{

            val request = NavDeepLinkRequest.Builder
                .fromUri(ConstantFragmentsDeepLinkUri.CreateVoter.toUri())

                .build()
            findNavController().navigate(request)
        }

        binding.timeLayout.setOnClickListener{

            val request = NavDeepLinkRequest.Builder
                .fromUri(ConstantFragmentsDeepLinkUri.SetTime.toUri())

                .build()
            findNavController().navigate(request)
        }
    }

}