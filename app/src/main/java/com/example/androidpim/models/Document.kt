package com.example.androidpim.models

data class Document(
    val _id: String,
    val claimedId: String,
    val createdAT: String,
    val docLanguage: String,
    val documentType: String,
    val numcopies: Int,
    val type: String
)