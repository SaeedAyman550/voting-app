package com.example.pressentation.ui.candidates

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.pressentation.R
import com.example.pressentation.adapter.CandidatesAdapter
import com.example.pressentation.databinding.FragmentCandidatesBinding
import com.example.domain.utils.ConstantFragmentsDeepLinkUri
import com.example.domain.utils.Resourse
import com.example.pressentation.sharedViewModel.GetAllCandidatesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Candidates : Fragment(R.layout.fragment_candidates) {
    lateinit var binding: FragmentCandidatesBinding

    @Inject
    lateinit var adapter: CandidatesAdapter
    private val view_model: GetAllCandidatesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCandidatesBinding.bind(view)

        adapter.setOnItemClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("${ConstantFragmentsDeepLinkUri.CandidatesDetails}/${it._id}".toUri())
                .build()
            findNavController().navigate(request)
        }

        lifecycleScope.launchWhenCreated {
            view_model.get_all_candidate_state_flow.collect {

                when (it) {
                    is Resourse.Loading ->
                        binding.candidatesProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.candidatesProgressPar.visibility = View.GONE
                        it.data?.let { it1 -> initRecyView(it1) }
                    }
                    is Resourse.Error -> {
                        Log.d("saeed", "${it.message} error ")
                        binding.candidatesProgressPar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun initRecyView(list: List<com.example.domain.models.Candidate>) {
        adapter.submitList(list)
        binding.candidatesRecycleView.adapter = adapter

    }

}