package com.example.data.remote.dto

data class TimeDto(
    val message: String?="",
    val result: List<TimeDtoResult>?= emptyList()
)