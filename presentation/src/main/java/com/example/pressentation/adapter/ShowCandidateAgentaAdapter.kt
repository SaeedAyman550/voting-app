package com.example.pressentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Agenda
import com.example.pressentation.R
import com.example.pressentation.databinding.AgentaShowingItemBinding
import javax.inject.Inject

class ShowCandidateAgentaAdapter @Inject constructor(): RecyclerView.Adapter<ShowCandidateAgentaAdapter.ShowCandidateAgentaHolder>() {

    private var agenda_list = mutableListOf<Agenda>()

     class ShowCandidateAgentaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var binding : AgentaShowingItemBinding
        init {
            binding= AgentaShowingItemBinding.bind(itemView)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCandidateAgentaHolder {

        return ShowCandidateAgentaHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.agenta_showing_item,parent,false))
    }

    override fun onBindViewHolder(holder: ShowCandidateAgentaHolder, position: Int) {
        val agenda_item=agenda_list.get(position)

        if(position==0){
            holder.binding.view.visibility=View.VISIBLE
            holder.binding.agentaShowingItemAgendaText.visibility=View.VISIBLE

        }else{
            holder.binding.view.visibility=View.GONE
            holder.binding.agentaShowingItemAgendaText.visibility=View.GONE
        }

        holder.binding.agentaShowingItemAbstractionText.text=agenda_item.abstraction
        holder.binding.agentaShowingItemSummaryText.text= agenda_item.summary
    }

    override fun getItemCount(): Int {
        return agenda_list.size
    }

    fun submitList(list: List<Agenda>?){
        if (list != null&&list is MutableList<Agenda>) {
            agenda_list = list
            notifyDataSetChanged()

        }
    }

}