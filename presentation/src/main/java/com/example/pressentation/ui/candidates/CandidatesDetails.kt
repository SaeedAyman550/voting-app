package com.example.pressentation.ui.candidates

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.domain.utils.ConstantFragmentsDeepLinkUri
import com.example.domain.utils.Resourse
import com.example.pressentation.R
import com.example.pressentation.adapter.ShowCandidateAgentaAdapter
import com.example.pressentation.databinding.FragmentCandidatesDetailsBinding
import com.example.pressentation.sharedViewModel.GetCandidateByIdViewModel
import com.example.pressentation.sharedViewModel.GetTimeViewModel
import com.example.pressentation.ui.utils.compareBetweenTwoDate
import com.example.pressentation.ui.utils.getCurrentTime
import com.example.pressentation.ui.utils.higherTime
import com.example.pressentation.ui.utils.lowerTime
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CandidatesDetails : Fragment(R.layout.fragment_candidates_details) {

    lateinit var binding: FragmentCandidatesDetailsBinding
    @Inject
    lateinit var adapter:ShowCandidateAgentaAdapter
    private  val candidateByIdViewModel: GetCandidateByIdViewModel by viewModels()
    private  val timeViewModel: GetTimeViewModel by viewModels()
    private val args:CandidatesDetailsArgs by navArgs()
    private var saveTime: com.example.domain.models.Time?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        candidateByIdViewModel.getCandidateById(args.candidateId)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding=FragmentCandidatesDetailsBinding.bind(view)


        binding.candidateDetailsVoteButton.setOnClickListener {
            saveTime?.let { handleTime(it) }
        }


        lifecycleScope.launchWhenCreated {
        candidateByIdViewModel.get_candidate_by_id_state_flow.collect {

            when (it) {
                is Resourse.Loading ->
                    binding.candidateDetailsProgressPar.visibility = View.VISIBLE

                is Resourse.Success -> {
                    binding.candidateDetailsProgressPar.visibility = View.GONE
                    it.data?.let { it1 -> setCandidateDataInViewBinding(it1) }
                    it.data?.let { candidate -> initRecyView(candidate.agenda_list) }
                }
                is Resourse.Error-> {
                    binding.candidateDetailsProgressPar.visibility = View.GONE
                    Log.d("saeed","${it.message} error ")
                }

            }

        }}

        lifecycleScope.launchWhenCreated {
            timeViewModel.get_time_state_flow.collect {
            when (it) {
                is Resourse.Loading ->
                    binding.candidateDetailsProgressPar.visibility = View.VISIBLE

                is Resourse.Success -> {
                    binding.candidateDetailsProgressPar.visibility = View.GONE
                    saveTime= it.data
                }
                is Resourse.Error-> {
                    binding.candidateDetailsProgressPar.visibility = View.GONE
                    Log.d("saeed", "${it.message} error ")
                }
            }

        }}


    }
    private fun initRecyView(list:List<com.example.domain.models.Agenda>){
        adapter.submitList(list)
        binding.candidatesDetailsRecy.adapter=adapter

    }
    private fun setCandidateDataInViewBinding(candidate: com.example.domain.models.Candidate){
        Glide.with(binding.root).load(candidate.image).into(binding.candidatesDetailsCircleImageView)
        binding.candidatesDetailsCodeText.text=candidate.candidate_code
        binding.candidatesDetailsNameText.text=candidate.name
        binding.candidateDeatailsPoliticalPartyText.text=candidate.political_party

    }



    private fun handleTime(time: com.example.domain.models.Time){
        val current_date = getCurrentTime()
        val compare_date = compareBetweenTwoDate(current_date, time.end_at)

        if (compare_date==lowerTime) {
            val request = NavDeepLinkRequest.Builder
                .fromUri("${ConstantFragmentsDeepLinkUri.VotingDailog}/${args.candidateId}".toUri())
                .build()
            findNavController().navigate(request)
        }
        else if (compare_date==higherTime)
            Toast.makeText(requireContext(), "voting is ended", Toast.LENGTH_LONG).show()
        else{

        }
    }


}