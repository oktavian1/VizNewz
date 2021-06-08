package com.example.viznews.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.viznews.data.NewsRepository
import com.example.viznews.data.network.response.DataNewsResponse
import com.example.viznews.utils.Resource

class NewsViewModel(private val repository: NewsRepository): ViewModel() {

    fun getNews(time: Int, idCategory: Int? = null): LiveData<Resource<List<DataNewsResponse>>> =
            repository.getNews(time, idCategory)

}