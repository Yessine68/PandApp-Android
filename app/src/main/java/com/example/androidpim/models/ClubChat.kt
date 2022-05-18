package com.example.androidpim.models

data class ClubChat(
    var _id: String?=null,
    var clubName: String?=null,
    var esmElclub: String?=null,
    var messageclubs: List<Messageclub>?=null,
    var clubImage: String?=null
)