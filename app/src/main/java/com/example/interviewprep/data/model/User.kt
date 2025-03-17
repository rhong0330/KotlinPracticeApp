package com.example.interviewprep.data.model

data class User (
    val id: Int,
    val name: String,
    val email: String,
    var avatar: String?
) {
    constructor(id: Int, name: String, email: String) : this(
        id, name, email, "https://robohash.org/$id.png"
    )
}