package com.example.data.remote.dto

data class CandidateResultDto(
    val __v: Int?=-1,
    val _id: String?="",
    val agendaSlug: List<AgendaDto>?= emptyList(),
    val agenda_list: List<AgendaDto>?= emptyList(),
    val candidate_code: String?="",
    val candidate_code_slug: String?="",
    val createdAt: String?="",
    val date_of_birth: String?="",
    val image: String?="",
    val name: String?="",
    val political_party: String?="",
    val updatedAt: String?="",
    val vote_count: Int?=-1,
    val voter_id: List<VoterIdDto>?= emptyList()
)

