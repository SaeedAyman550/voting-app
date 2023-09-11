package com.example.domain.models

data class Voter(

    val _id: String="",
    val candidate_id: String="",
    val city: String="",
    val date_of_birth: String="",
    val image: String="",
    val isVote: Boolean=false,
    val name: String="",
    val personal_id: Long=-1,
    val refreshToken: String="",
)
