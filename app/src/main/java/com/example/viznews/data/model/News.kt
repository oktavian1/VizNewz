package com.example.viznews.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
        val id: Int,
        val source: String,
        val date: String,
        val image: String,
        val title: String,
        val description: String,
        val rating: Int,
        val link: String,
        val tags: List<List<String>>
):Parcelable
