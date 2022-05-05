package com.example.androidpim.models



data class UserLoggedIn (
    val _id: String? = null,
    var identifant: String? = null,
    var FirstName: String? = null,
    val LastName: String? = null,
    var className: String? = null,
    var description: String? = null,
    var email: String? = null,
    var password: String? = null,
    var phoneNumber: Int? = null,
    val profilePicture: String? = null,
    val role: String? = null,
    val social: Boolean? = null,
    val verified: Boolean? = null,
    val token : String? = null
)

data class User (
    var identifant: String? = null,
    var FirstName: String? = null,
    var LastName: String? = null,
    var className: String? = null,
    var description: String? = null,
    var email: String? = null,
    var password: String? = null,
    var phoneNumber: Int? = null,
    var profilePicture: String? = null,
    var role: String? = null,
    var social: Boolean? = null,
    var verified: Boolean? = null
)

data class UserReset (
    var email: String? = null
)

data class UserResetResponse (
    val msgg: String? = null

)

data class CheckResponse (
    val check: Boolean? = null
)
data class Check (
    var code: String? = null
)

data class UserResetPassword (
    var email: String? = null,
    var password: String? = null

)


