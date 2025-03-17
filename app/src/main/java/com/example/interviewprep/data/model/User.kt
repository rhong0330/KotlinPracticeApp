package com.example.interviewprep.data.model

data class User (
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String? = "https://robohash.org/${id}.png"
)