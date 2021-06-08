package com.example.viznews.di

import android.content.Context
import com.example.viznews.data.NewsRepository
import com.example.viznews.data.network.RemoteDataSource
import com.example.viznews.data.network.remote.ApiServices

object Injection {

    fun provideRepository(context: Context): NewsRepository{
        val remoteDataSource = RemoteDataSource.getInstance(ApiServices)
        return NewsRepository.getInstance(remoteDataSource)
    }

}