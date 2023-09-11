package com.example.pressentation.ui.voter_info

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.domain.models.Candidate
import com.example.domain.models.Time
import com.example.domain.models.Voter
import com.example.domain.utils.Constants
import com.example.domain.utils.Resourse
import com.example.pressentation.R
import com.example.pressentation.databinding.FragmentVoterInfoBinding
import com.example.pressentation.sharedViewModel.GetCandidateByIdViewModel
import com.example.pressentation.sharedViewModel.GetMyVoterViewModel
import com.example.pressentation.sharedViewModel.GetTimeViewModel
import com.example.pressentation.sharedViewModel.TokenManagerViewModel
import com.example.pressentation.ui.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoterInfo : Fragment(R.layout.fragment_voter_info) {


    private val deleteVotingViewModel: VoterInfoViewModel by viewModels()
    private val candidateByIdViewModel: GetCandidateByIdViewModel by viewModels()
    private val myVoterViewModel: GetMyVoterViewModel by viewModels()
    private val timeViewModel: GetTimeViewModel by viewModels()
    private val token_view_model: TokenManagerViewModel by viewModels()
    lateinit var binding: FragmentVoterInfoBinding
    private var voter: Voter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVoterInfoBinding.bind(view)


        binding.userInfoLogOutButton.setOnClickListener {

            lifecycleScope.launchWhenCreated {
                val resourse = token_view_model.setVoterToken(Constants.empty_voter)

                handleSetToken(resourse)

            }
        }



        lifecycleScope.launchWhenCreated {
            myVoterViewModel.get_my_voter_state_flow.collect {

                when (it) {
                    is Resourse.Loading ->
                        binding.voterInfoProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.voterInfoProgressPar.visibility = View.GONE
                        val v = it.data
                        voter = v
                        if (v != null) {
                            timeViewModel.getTime()
                            if (v.isVote) {
                                binding.userInfoIncludeVoteCandidate.root.visibility = View.VISIBLE
                                binding.userInfoIncludeNotVoteCandidate.root.visibility = View.GONE

                            } else {
                                binding.userInfoIncludeVoteCandidate.root.visibility = View.GONE
                                binding.userInfoIncludeNotVoteCandidate.root.visibility =
                                    View.VISIBLE
                            }
                            setVoterDataInViewBinding(v)

                        }
                    }
                    is Resourse.Error -> {
                        binding.voterInfoProgressPar.visibility = View.GONE
                        Log.d("saeed", "${it.message} error ")
                    }


                }

            }
        }
        lifecycleScope.launchWhenCreated {
            candidateByIdViewModel.get_candidate_by_id_state_flow.collect {

                when (it) {
                    is Resourse.Loading ->
                        binding.voterInfoProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.voterInfoProgressPar.visibility = View.GONE

                        val candidate = it.data
                        if (candidate != null) {
                            setCandidateDataInViewBinding(candidate)

                        }
                    }
                    is Resourse.Error -> {
                        binding.voterInfoProgressPar.visibility = View.GONE
                        Log.d("saeed", "${it.message} error ")
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            timeViewModel.get_time_state_flow.collect {
                when (it) {
                    is Resourse.Loading ->
                        binding.voterInfoProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        if (it.data != null) {
                            binding.voterInfoProgressPar.visibility = View.GONE
                            voter?.candidate_id?.let { it1 ->
                                candidateByIdViewModel.getCandidateById(it1)
                            }
                            handleTimeToVoteOrDelete(it.data!!)
                        }
                    }
                    is Resourse.Error -> {
                        Log.d("saeed", "${it.message} error ")
                        binding.voterInfoProgressPar.visibility = View.GONE
                    }
                }

            }
        }
        lifecycleScope.launchWhenCreated {
            deleteVotingViewModel.delete_voting_state_flow.collect {

                when (it) {
                    is Resourse.Loading ->
                        binding.voterInfoProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.voterInfoProgressPar.visibility = View.GONE
                        Toast.makeText(requireContext(), "delete vote sucess", Toast.LENGTH_LONG).show()
                        myVoterViewModel.getMyVoter()
                    }
                    is Resourse.Error -> {
                        binding.voterInfoProgressPar.visibility = View.GONE
                        Log.d("saeed", "${it.message} error ")
                    }
                }
            }
        }
    }


    private fun setVoterDataInViewBinding(voter: Voter) {

        Glide.with(binding.root).load(voter.image).into(binding.userInfoCircleImageView)
        binding.userInfoNameText.text = voter.name
        binding.userInfoDateOfBirthText.text = voter.date_of_birth
        binding.userInfoCityText.text = voter.city
        binding.userInfoIdText.text = voter.personal_id.toString()

    }

    private fun setCandidateDataInViewBinding(candidate: Candidate) {

        binding.userInfoIncludeVoteCandidate.userInfoIncludeVoteCandidateCodeText.text =
            candidate.candidate_code
        binding.userInfoIncludeVoteCandidate.userInfoIncludeVoteCandidateNameText.text =
            candidate.name
    }


    private fun handleTimeToVoteOrDelete(time: Time) {
        val current_date = getCurrentTime()
        val compare_date = compareBetweenTwoDate(current_date, time.end_at)

        if (binding.userInfoIncludeVoteCandidate.root.visibility == View.VISIBLE) {
            binding.userInfoIncludeVoteCandidate.userInfoIncludeVoteCandidateDeleteVoteButton.setOnClickListener {

                if (compare_date == lowerTime)
                    deleteVotingViewModel.deleteVoting(voter!!.candidate_id, voter!!._id)
                else if (compare_date == higherTime)
                    Toast.makeText(requireContext(), "voting is ended", Toast.LENGTH_LONG).show()
                else {

                }
            }
        } else {
            binding.userInfoIncludeNotVoteCandidate.userInfoIncludeNotVoteCandidateVoteButton.setOnClickListener {

                if (compare_date == lowerTime) {
                    findNavController().popBackStack()

                } else if (compare_date == higherTime)
                    Toast.makeText(requireContext(), "voting is ended", Toast.LENGTH_LONG).show()
                else {

                }
            }

        }

    }

    private fun handleSetToken(resourse: Resourse<String>) {

        when (resourse) {
            is Resourse.Loading -> {}

            is Resourse.Success -> {
                findNavController().popBackStack()
                findNavController().popBackStack()

            }

            is Resourse.Error -> {
                Log.d("saeed", "${resourse.message} error ")
            }


        }
    }


}