package com.example.pressentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.models.Candidate
import com.example.pressentation.R
import com.example.pressentation.databinding.WinerCandidateItemBinding
import javax.inject.Inject

class VotingResultCandidateAdapter @Inject constructor() :
    RecyclerView.Adapter<VotingResultCandidateAdapter.VotingResultCandidateHolder>() {

    private var candidates_list = mutableListOf<Candidate>()

    class VotingResultCandidateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var binding : WinerCandidateItemBinding
        init {
            binding= WinerCandidateItemBinding.bind(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VotingResultCandidateHolder {
        return VotingResultCandidateHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.winer_candidate_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VotingResultCandidateHolder, position: Int) {

        val binding=holder.binding
        val candidate_item=candidates_list.get(position)
            Glide.with(binding.root)
                .load(candidate_item.image)
                .into(binding.winerCandidatesItemCircleImageView)
            binding.winerCandidatesItemCodeText.text = candidate_item.candidate_code
            binding.winerCandidatesItemNameText.text = candidate_item.name
            binding.winerCandidatesItemPercentText.text = "Number Of Voter = ${candidate_item.voter_id.size}"
    }

    override fun getItemCount(): Int {
        return candidates_list.size
    }

    fun submitList(list: List<Candidate>){
        if (list is MutableList<Candidate>) {
            candidates_list = list
            notifyDataSetChanged()
        }

    }

}