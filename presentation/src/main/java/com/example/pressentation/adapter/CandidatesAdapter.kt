package com.example.pressentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.models.Candidate
import com.example.pressentation.R
import com.example.pressentation.databinding.CandidatesItemBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class CandidatesAdapter @Inject constructor() :
    RecyclerView.Adapter<CandidatesAdapter.CandidatesHolder>() {

    private var candidates_list = mutableListOf<Candidate>()
    private var onItemClickListener: ((candidate: Candidate) -> Unit)? = null


    class CandidatesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var binding: CandidatesItemBinding

        init {
            binding = CandidatesItemBinding.bind(itemView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidatesHolder {

        return CandidatesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.candidates_item, null, false)
        )
    }

    override fun onBindViewHolder(holder: CandidatesHolder, position: Int) {

        var candidate_item = candidates_list.get(position)

        setDataInBinding(candidate_item, holder)
        holder.binding.root.setOnClickListener {

            onItemClickListener?.let { it1 -> it1(candidate_item) }
        }

    }

    override fun getItemCount(): Int {
        return candidates_list.size
    }


    private fun setDataInBinding(candidate: Candidate, holder: CandidatesHolder) {
        holder.binding.apply {
            Glide.with(this.root).load(candidate.image).into(candidatesCircleImageView)
            candidatesNameText.text = candidate.name
            candidatesCodeText.text = candidate.candidate_code
            candidatesDateOfBirthText.text = candidate.date_of_birth

        }
    }

    fun submitList(list: List<Candidate>) {
        if (list is MutableList<Candidate>) {
            candidates_list = list
            notifyDataSetChanged()
        }
    }

    fun setOnItemClickListener(listener: (Candidate) -> Unit) {
        onItemClickListener = listener

    }

}