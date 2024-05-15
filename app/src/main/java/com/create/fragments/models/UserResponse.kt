package com.create.fragments.models

data class UserResponse(
    val error: String,
    val message: String,
    val user:User
)