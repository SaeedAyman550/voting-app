package com.example.data.mapper

import com.example.data.remote.dto.CandidateResultDto
import com.example.domain.models.Candidate
import com.example.domain.utils.Constants
import com.example.pressentation.ui.utils.convertFormatDate

object CandidateMapper:Mapper<CandidateResultDto?, Candidate> {
    private val local_host=Constants.local_host
    override fun map(input: CandidateResultDto?): Candidate {
        if (input!=null) {
            var valid_image = input.image?.replace("localhost:3030", "${local_host}") ?:""
            return Candidate(
                input._id ?: "",
                input.agenda_list?.map { AgendaMapper.map(it) } ?: emptyList(),
                input.candidate_code ?: "",
                convertFormatDate(input.date_of_birth ?: ""),
                valid_image ,
                input.name ?: "",
                input.political_party ?: "",
                input.vote_count ?: -1,
                input.voter_id?.map { it._id ?: "" } ?: emptyList()
            )
        }
        else
            return Candidate()
    }



}