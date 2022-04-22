package com.example.androidpim.models



data class UserLoggedIn (
    val _id: String? = null,
    val identifant: String? = null,
    val FirstName: String? = null,
    val LastName: String? = null,
    val className: String? = null,
    val description: String? = null,
    var email: String? = null,
    var password: String? = null,
    val phoneNumber: Int? = null,
    val profilePicture: String? = null,
    val role: String? = null,
    val social: Boolean? = null,
    val verified: Boolean? = null,
    val token : String? = null
)

data class User (
    val identifant: String? = null,
    val FirstName: String? = null,
    val LastName: String? = null,
    val className: String? = null,
    val description: String? = null,
    var email: String? = null,
    var password: String? = null,
    var phoneNumber: Int? = null,
    val profilePicture: String? = null,
    val role: String? = null,
    val social: Boolean? = null,
    val verified: Boolean? = null
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


