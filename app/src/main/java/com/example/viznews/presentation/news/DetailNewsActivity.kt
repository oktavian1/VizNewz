package com.example.viznews.presentation.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.viznews.R
import com.example.viznews.data.model.News
import com.example.viznews.databinding.ActivityDetailNewsBinding


class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNewsBinding
    companion object{
        const val DATA = "data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataNews = intent.getParcelableExtra<News>(DATA)
        showDetailNews(dataNews)
    }

    private fun showDetailNews(news: News?){
        news.let { news ->
            val tag = news?.tags
            val ss: ArrayList<String> = arrayListOf()
            if (tag != null) {
                for (i in tag.iterator()){
                    for (j in i.iterator()){
                        ss.add(j)
                    }
                }
            }
            binding.tvTags.text = ss.toString().dropLast(1).drop(1)
            binding.tvTitleDetail.text = news?.title
            binding.tvDescription.text = news?.description
            if(news?.image != ""){
                Glide.with(this)
                        .load(news?.image)
                        .apply(RequestOptions().override(600, 600))
                        .placeholder(R.drawable.image)
                        .into(binding.imgDetail)
            }else{
                Glide.with(this)
                        .load(R.drawable.image)
                        .apply(RequestOptions().override(600, 600))
                        .placeholder(R.drawable.image)
                        .into(binding.imgDetail)
            }

            binding.btnEntireNews.setOnClickListener{
                val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(news?.link))
                startActivity(openBrowserIntent)
            }
        }
    }
}