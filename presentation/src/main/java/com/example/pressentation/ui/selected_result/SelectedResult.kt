package com.example.pressentation.ui.selected_result

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pressentation.R
import com.example.pressentation.databinding.FragmentSelectedResultBinding
import com.example.domain.utils.Resourse
import com.example.pressentation.sharedViewModel.GetMyVoterViewModel
import com.example.pressentation.sharedViewModel.GetTimeViewModel
import com.example.pressentation.ui.utils.compareBetweenTwoDate
import com.example.pressentation.ui.utils.getCurrentTime
import com.example.pressentation.ui.utils.higherTime
import com.example.pressentation.ui.utils.lowerTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectedResult : Fragment(R.layout.fragment_selected_result) {

    private val myVoterViewModel: GetMyVoterViewModel by viewModels()
    private val timeViewModel: GetTimeViewModel by viewModels()

    lateinit var binding: FragmentSelectedResultBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSelectedResultBinding.bind(view)


        lifecycleScope.launchWhenCreated {
            myVoterViewModel.get_my_voter_state_flow.collect {

                when (it) {
                    is Resourse.Loading ->
                        binding.myResultProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.myResultProgressPar.visibility = View.GONE
                        if (it.data!!.isVote) {
                            binding.includeShowUserVote.root.visibility = View.VISIBLE
                            binding.includeShowNotVoting.root.visibility = View.GONE
                            binding.includeShowUserVote.showNotVotingButton.setOnClickListener {
                                findNavController().popBackStack()
                            }
                        } else {
                            binding.includeShowNotVoting.root.visibility = View.VISIBLE
                            binding.includeShowUserVote.root.visibility = View.GONE
                        }
                    }
                    is Resourse.Error -> {
                        binding.myResultProgressPar.visibility = View.GONE
                        Log.d("saeed", "${it.message} error ")
                    }

                }

            }
        }

        lifecycleScope.launchWhenCreated {
            timeViewModel.get_time_state_flow.collect {
                when (it) {
                    is Resourse.Loading ->
                        binding.myResultProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.myResultProgressPar.visibility = View.GONE
                        handleTime(it.data!!)
                    }
                    is Resourse.Error -> {
                        binding.myResultProgressPar.visibility = View.GONE
                        Log.d("saeed", "${it.message} error ")
                    }


                }
            }

        }


    }


    private fun handleTime(time: com.example.domain.models.Time) {
        val current_date = getCurrentTime()
        val compare_date = compareBetweenTwoDate(current_date, time.end_at)

        if (compare_date == lowerTime) {
            binding.includeShowNotVoting.showNotVotingButton.setOnClickListener {
                findNavController().popBackStack()
            }
        } else if (compare_date == higherTime)
            Toast.makeText(requireContext(), "voting is ended", Toast.LENGTH_LONG).show()
        else {

        }
    }


}

