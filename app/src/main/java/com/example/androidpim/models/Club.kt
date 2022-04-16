package com.example.androidpim.models

data class Club(
    val clubLogo: String? = null,
    val clubName: String? = null,
    val clubOwner: String? = null,
    val description: String? = null,
    val login: String? = null,
    val password: String? = null,
    val social: Boolean? = null,
    val verified: Boolean? = null
)

data class ClubLoggedIn(
    val clubLogo: String? = null,
    val clubName: String? = null,
    val clubOwner: String? = null,
    val description: String? = null,
    val login: String? = null,
    val password: String? = null,
    val social: Boolean? = null,
    val verified: Boolean? = null,
    val token : String? = null
)