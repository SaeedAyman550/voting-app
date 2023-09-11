package com.example.data.remote.dto

data class VoterDto(
    val message: String?="",
    val status: String?="",
    val voter: VoterResultDto?=VoterResultDto()
)