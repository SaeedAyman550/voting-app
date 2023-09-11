package com.example.pressentation

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.pressentation.databinding.FragmentSplashBinding
import com.example.domain.utils.ConstantFragmentsDeepLinkUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class splash : Fragment(R.layout.fragment_splash) {

    lateinit var binding:FragmentSplashBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding=FragmentSplashBinding.bind(view)

        Handler().postDelayed({

            findNavController().popBackStack()

            val request = NavDeepLinkRequest.Builder
                .fromUri(ConstantFragmentsDeepLinkUri.AdminOrVoter.toUri())

                .build()
            findNavController().navigate(request)
        },1500)


    }

}