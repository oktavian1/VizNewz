package com.example.viznews.utils


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.viznews.data.model.News
import com.example.viznews.data.model.OverallSentiment
import com.example.viznews.data.model.TextRating
import com.example.viznews.data.network.response.*
import com.example.viznews.presentation.dashboard.DetailActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object DataMapper {

    fun mapToOverall(overall: DataOverallSentiment): OverallSentiment{
        return OverallSentiment(
            positive = overall.positive,
            negative = overall.negative,
            neutral = overall.neutral
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun minusDayHelper(day: Long): String{
        val date = LocalDateTime.now().minusDays(day)
        val formatter = DateTimeFormatter.ofPattern("MMM-dd")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToDataEntry(data: List<DataOverallChart>): List<DetailActivity.CustomDataEntry>{
        val dataEntry: ArrayList<DetailActivity.CustomDataEntry> = arrayListOf()

        for ((i, e) in data.withIndex()){
            val mapping = DetailActivity.CustomDataEntry(
                    x = minusDayHelper(i.toLong()),
                    value = e.positive,
                    value2 = e.negative,
                    value3 = e.neutral
            )
            Log.d("asak", minusDayHelper(i.toLong()))
            dataEntry.add(mapping)
        }

        return dataEntry
    }

    fun mapToPositiveTextRating(data: List<PositiveItem>): List<TextRating>{
        val listData = ArrayList<TextRating>()

        data.map {
            val textRating = TextRating(
                    text = it.word.toString(),
                    rating = it.frequency!!,
                    id = 0
            )
            listData.add(textRating)
        }

        return listData
    }

    fun mapToNegativeTextRating(data: List<NegativeItem>): List<TextRating>{
        val listData = ArrayList<TextRating>()

        data.map {
            val textRating = TextRating(
                    text = it.word.toString(),
                    rating = it.frequency!!,
                    id = 0
            )
            listData.add(textRating)
        }

        return listData
    }

    fun mapNewsResponseToNews(data: List<DataNewsResponse>): List<News>{
        val listData = ArrayList<News>()

        data.map {
            val news = News(
                    id = it.id,
                    image = it.image,
                    date = it.date,
                    title = it.title,
                    description = it.content,
                    source = it.portal,
                    link = it.link,
                    rating = it.sentiment,
                    tags = it.tags
            )
            listData.add(news)
        }

        return listData
    }

}
