package com.example.android.politicalpreparedness.network.models

data class ElectionModel(
       val id: Long,
       val name: String,
       val electionDay: String,
       val ocdDivisionId: String
)