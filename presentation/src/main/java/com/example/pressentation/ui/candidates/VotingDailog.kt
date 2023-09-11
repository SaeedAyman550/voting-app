package com.example.pressentation.ui.candidates

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pressentation.R
import com.example.pressentation.databinding.FragmentVotingDailogBinding
import com.example.domain.utils.Resourse
import com.example.pressentation.sharedViewModel.GetMyVoterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VotingDailog : DialogFragment(R.layout.fragment_voting_dailog) {


    lateinit var binding:FragmentVotingDailogBinding
    private val setTimeViewModel:CandidateViewModel by viewModels()
    private val myVoterViewModel:GetMyVoterViewModel by viewModels()
    private val args:VotingDailogArgs by navArgs()
    private var voter: com.example.domain.models.Voter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentVotingDailogBinding.bind(view)

        setDailogBackground()
        setWidthPercent(80)

        binding.votingDailogYesButton.setOnClickListener {

            if (voter!=null) {
                if (voter!!.isVote)
                    Toast.makeText(requireContext(), "you are voted before", Toast.LENGTH_LONG)
                        .show()
                else
                    setTimeViewModel.setVoting(args.candidateId, voter!!._id)
            }
        }
        binding.votingDailogNoButton.setOnClickListener {

            findNavController().popBackStack()
        }


        lifecycleScope.launchWhenCreated {
            myVoterViewModel.get_my_voter_state_flow.collect {

            when (it) {
                is Resourse.Loading ->{}

                is Resourse.Success -> voter=it.data

                is Resourse.Error-> {
                    if (it.message=="init mutable state")
                    else
                        Log.d("saeed","${it.message} error ")


                }

            }

        }
        }
        lifecycleScope.launchWhenCreated {
        setTimeViewModel.set_voting_state_flow.collect {

            when (it) {
                is Resourse.Loading ->{}
                is Resourse.Success -> {
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(), "success voted", Toast.LENGTH_LONG).show()
                }
                is Resourse.Error->
                    Log.d("saeed","${it.message} error ")
            }

        }
        }

    }



    fun DialogFragment.setDailogBackground() =
        dialog?.window?.setBackgroundDrawableResource(R.drawable.voting_dailog_background_raduis)

    fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics

        val rect = dm.run {
            Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}