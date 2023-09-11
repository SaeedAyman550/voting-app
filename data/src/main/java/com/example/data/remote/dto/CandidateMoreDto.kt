package com.example.data.remote.dto

data class CandidateMoreDto(
    val message: String?="",
    val result: List<CandidateResultDto>?= emptyList()
)
