package com.example.androidpim.models

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("image")

    val image: String,
    @SerializedName("objet")

    val objet: String,
    @SerializedName("place")

    val place: String,
    @SerializedName("publisheId")

    val publisheId: String,
    @SerializedName("publishedAt")

    val publishedAt: String,
    @SerializedName("state")

    val state: Boolean,
    @SerializedName("type")

    val type: String
){}
