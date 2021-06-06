package com.example.viznews.presentation.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viznews.R

class DetailNewsActivity : AppCompatActivity() {
    companion object{
        const val DATA = "data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
    }
}