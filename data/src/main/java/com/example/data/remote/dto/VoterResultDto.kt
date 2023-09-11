package com.example.data.remote.dto

data class VoterResultDto(
    val __v: Int?=-1,
    val _id: String?="",
    val candidate_id: List<CandidateIdDto>?= emptyList(),
    val city: String?="",
    val createdAt: String?="",
    val date_of_birth: String?="",
    val faceIdImage: String?="",
    val image: String?="",
    val isVote: Boolean?=false,
    val name: String?="",
    val personal_id: Long?=-1L,
    val refreshToken: String?="",
    val role: String?="",
    val updatedAt: String?=""
)