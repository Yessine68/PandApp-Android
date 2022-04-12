package com.example.androidpim.models

data class Post(
    val _id: String,
    val image: String,
    val `object`: String,
    val place: String,
    val publisheId: String,
    val publishedAt: String,
    val state: Boolean,
    val type: String
){}