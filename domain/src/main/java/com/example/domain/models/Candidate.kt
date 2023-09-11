package com.example.domain.models

data class Candidate(

    val _id: String="",
    val agenda_list: List<com.example.domain.models.Agenda> =emptyList(),
    val candidate_code: String="",
    val date_of_birth: String="",
    val image: String="",
    val name: String="",
    val political_party: String="",
    val vote_count: Int=-1,
    val voter_id: List<String> = emptyList()
)
