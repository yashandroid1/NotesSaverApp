package com.create.fragments.models

data class GetNotes(
    val error: String,
    val message: String,
    val UserNotes: List<UserNote>
)