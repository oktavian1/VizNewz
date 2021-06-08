package com.example.viznews.data

import androidx.lifecycle.LiveData
import com.example.viznews.data.model.OverallSentiment
import com.example.viznews.data.network.response.DataNewsResponse
import com.example.viznews.data.network.response.DataOverallChart
import com.example.viznews.data.network.response.DataWords
import com.example.viznews.utils.Resource

interface NewsDataSource {

    fun getOverallSentiment(time: Int, idCategory: Int? = null): LiveData<Resource<OverallSentiment>>

    fun getOverallChart(time: Int, idCategory: Int? = null): LiveData<Resource<List<DataOverallChart>>>

    fun getNews(time: Int, idCategory: Int? = null): LiveData<Resource<List<DataNewsResponse>>>

    fun getWords(time: Int, idCategory: Int? = null): LiveData<Resource<DataWords>>

}