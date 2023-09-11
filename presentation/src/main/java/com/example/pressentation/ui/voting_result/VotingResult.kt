package com.example.pressentation.ui.voting_result

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.domain.models.Candidate
import com.example.domain.models.Time
import com.example.domain.utils.Resourse
import com.example.pressentation.R
import com.example.pressentation.adapter.VotingResultCandidateAdapter
import com.example.pressentation.databinding.FragmentVotingResultBinding
import com.example.pressentation.sharedViewModel.GetAllCandidatesViewModel
import com.example.pressentation.sharedViewModel.GetTimeViewModel
import com.example.pressentation.ui.utils.compareBetweenTwoDate
import com.example.pressentation.ui.utils.getCurrentTime
import com.example.pressentation.ui.utils.higherTime
import com.example.pressentation.ui.utils.lowerTime
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VotingResult : Fragment(R.layout.fragment_voting_result) {

    @Inject
    lateinit var adapter: VotingResultCandidateAdapter
    lateinit var binding: FragmentVotingResultBinding
    private val allCandidatesViewModel: GetAllCandidatesViewModel by viewModels()
    private val timeViewModel: GetTimeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVotingResultBinding.bind(view)


        lifecycleScope.launchWhenCreated {
            timeViewModel.get_time_state_flow.collect {
                when (it) {
                    is Resourse.Loading ->
                        binding.votingResultProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.votingResultProgressPar.visibility = View.GONE
                        handleTime(it.data!!)
                    }
                    is Resourse.Error -> {
                        binding.votingResultProgressPar.visibility = View.GONE
                        Log.d("saeed", "${it.message} error ")
                    }


                }

            }
        }

        lifecycleScope.launchWhenCreated {
            allCandidatesViewModel.get_all_candidate_state_flow.collect {

                when (it) {
                    is Resourse.Loading ->
                        binding.votingResultProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.votingResultProgressPar.visibility = View.GONE

                        val orderCandidate = it.data?.let { it1 -> orderCandidateList(it1) }
                        val candidate_winer = orderCandidate?.get(0)
                        val list_without_winer = orderCandidate?.filterIndexed { index, candidate ->
                            index != 0
                        }
                        setWinnerCandidateInViewBinding(candidate_winer!!)
                        initRecyView(list_without_winer!!)

                    }
                    is Resourse.Error -> {
                        binding.votingResultProgressPar.visibility = View.GONE
                        Log.d("saeed", "${it.message} error ")
                    }


                }
            }
        }
    }


    private fun handleTime(time: Time) {
        val current_date = getCurrentTime()
        val compare_date = compareBetweenTwoDate(current_date, time.end_at)


        if (compare_date == lowerTime) {

            binding.votingResultIncludeAppear.root.visibility = View.GONE
            binding.votingResultIncludeNotAppear.root.visibility = View.VISIBLE
            binding.votingResultIncludeNotAppear.votingResultNotAppearNotEndTimeValueText.text =
                "${binding.votingResultIncludeNotAppear.votingResultNotAppearNotEndTimeValueText.text} ${time.end_at}"

        } else if (compare_date == higherTime) {

            binding.votingResultIncludeAppear.root.visibility = View.VISIBLE
            binding.votingResultIncludeNotAppear.root.visibility = View.GONE
            allCandidatesViewModel.getAllCandidates()
        } else {
        }

    }

    private fun setWinnerCandidateInViewBinding(candidate: Candidate) {
        val bind = binding.votingResultIncludeAppear
        Glide.with(bind.root).load(candidate.image)
            .into(bind.winerCandidateCircleImageView)
        bind.winerCandidateCodeText.text = candidate.candidate_code
        bind.winerCandidateNameText.text = candidate.name
        bind.winerCandidatePercentText.text = "Number Of Voter =${candidate.voter_id.size}"

    }

    private fun initRecyView(list: List<Candidate>) {
        adapter.submitList(list)
        binding.votingResultIncludeAppear.winerCandidateRecy.adapter = adapter

    }

    private fun orderCandidateList(list_candidate: List<Candidate>): List<Candidate> =
        list_candidate.sortedByDescending { it.voter_id.size }

}