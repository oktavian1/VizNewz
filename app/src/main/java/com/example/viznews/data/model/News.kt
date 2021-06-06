package com.example.viznews.data.model

data class News(
    val id: Int,
    val source: String,
    val date: String,
    val image: Int,
    val title: String,
    val description: String,
    val rating: String
)
