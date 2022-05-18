package com.example.androidpim.models

data class Club(
    var _id: String?=null,
    var clubLogo: String? = null,
    var clubName: String? = null,
    var clubOwner: String? = null,
    var description: String? = null,
    var login: String? = null,
    var password: String? = null,
    var social: Boolean? = null,
    var verified: Boolean? = null
)

data class ClubLoggedIn(
    val clubLogo: String? = null,
    val clubName: String? = null,
    val clubOwner: String? = null,
    val description: String? = null,
    var login: String? = null,
    var password: String? = null,
    val social: Boolean? = null,
    val verified: Boolean? = null,
    val token : String? = null
)