package com.example.data.remote.dto

data class TimeDtoResult(
    val __v: Int?=-1,
    val _id: String?="",
    val createdAt: String?="",
    val end_at: List<String>?= emptyList(),
    val hour_duration: Int?=-1,
    val start_at: List<String>?= emptyList(),
    val updatedAt: String?="",
    val voting_status: Boolean?=false
)