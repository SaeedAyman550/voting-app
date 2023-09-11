package com.example.pressentation.ui.candidates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.pressentation.R
import com.example.pressentation.databinding.FragmentAcceptVotingBinding

class AcceptVoting : Fragment(R.layout.fragment_accept_voting) {

    lateinit var binding:FragmentAcceptVotingBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentAcceptVotingBinding.bind(view)

        binding.showNotVotingButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}