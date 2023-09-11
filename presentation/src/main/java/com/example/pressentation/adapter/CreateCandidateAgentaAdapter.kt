package com.example.pressentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Agenda
import com.example.pressentation.R
import com.example.pressentation.databinding.AgentaCreationItemBinding
import javax.inject.Inject

class CreateCandidateAgentaAdapter @Inject constructor():
    RecyclerView.Adapter<CreateCandidateAgentaAdapter.CreateCandidateAgendaHolder>() {


    private val diffUtil = object : DiffUtil.ItemCallback<Agenda>() {
        override fun areItemsTheSame(oldItem:Agenda, newItem:Agenda): Boolean =
            oldItem.abstraction==newItem.abstraction && oldItem.summary==newItem.summary

        override fun areContentsTheSame(oldItem: Agenda, newItem: Agenda): Boolean =
            oldItem==newItem
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)


    class CreateCandidateAgendaHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        lateinit var binding : AgentaCreationItemBinding
        init {
            binding= AgentaCreationItemBinding.bind(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateCandidateAgendaHolder {
        return CreateCandidateAgendaHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.agenta_creation_item,null,false))
    }

    override fun onBindViewHolder(holder: CreateCandidateAgendaHolder, position: Int) {
        val item= getCandidateAgendaList()[position]

        holder.binding.agentaCreationItemAbstractionEditText.addTextChangedListener {
            item.abstraction=holder.binding.agentaCreationItemAbstractionEditText.text.toString()
        }

        holder.binding.agentaCreationItemSummaryEditText.addTextChangedListener {
            item.summary=holder.binding.agentaCreationItemSummaryEditText.text.toString()
        }
        holder.binding.agentaCreationItemSummaryEditText.setText(item.summary)
        holder.binding.agentaCreationItemAbstractionEditText.setText(item.abstraction)

        holder.binding.createAgentaItemRejectImage.setOnClickListener{
            removeAtPositionItemCandidate(asyncListDiffer.currentList.indexOf(item))
        }
    }


    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    fun submitList(list: List<Agenda>) =asyncListDiffer.submitList(list)


    fun addItemCandidate() {
        val list= mutableListOf<Agenda>()
        asyncListDiffer.currentList.forEach {
            list.add(it)
        }
        list.add(Agenda())
        submitList(list)
    }

    fun removeAtPositionItemCandidate(position:Int) {
        if (asyncListDiffer.currentList.size==1)
            submitList(emptyList())
        else {
            var list = mutableListOf<Agenda>()
            asyncListDiffer.currentList.forEachIndexed { index, agenda ->
                if (index != position)
                    list.add(agenda)
            }
            submitList(list)
        }
    }

    fun getCandidateAgendaList():List<Agenda> =asyncListDiffer.currentList


}