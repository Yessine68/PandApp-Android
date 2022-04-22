package com.example.androidpim.models

data class Document(
    val _id: String? = null,
    var claimedId: String? = null,
    val createdAT: String? = null,
    var docLanguage: String? = null,
    var documentType: String? = null,
    var numcopies: Int? = null,
    val type: String? = null
)