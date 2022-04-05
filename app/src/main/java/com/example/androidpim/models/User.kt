package com.example.androidpim.models

data class User(
    val FirstName: String? = null,
    val LastName: String? = null,
    val className: String? = null,
    val description: String? = null,
    var email: String? = null,
    val identifant: String? = null,
    var password: String? = null,
    val phoneNumber: Int? = null,
    val profilePicture: String? = null,
    val role: String? = null,
    val social: Boolean? = null,
    val verified: Boolean? = null
)

data class UserLoggedIn (
    val FirstName: String? = null,
    val LastName: String? = null,
    val className: String? = null,
    val description: String? = null,
    var email: String? = null,
    val identifant: String? = null,
    var password: String? = null,
    val phoneNumber: Int? = null,
    val profilePicture: String? = null,
    val role: String? = null,
    val social: Boolean? = null,
    val verified: Boolean? = null,
    val token : String? = null
)
