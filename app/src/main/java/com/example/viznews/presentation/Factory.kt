@file:Suppress("UNCHECKED_CAST")

package com.example.viznews.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viznews.data.NewsRepository
import com.example.viznews.di.Injection
import com.example.viznews.presentation.dashboard.DetailViewModel
import com.example.viznews.presentation.news.NewsViewModel

class Factory(
    private val repository: NewsRepository
): ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: Factory? = null

        fun getInstance(context: Context): Factory =
            instance?: synchronized(this){
                instance ?: Factory(Injection.provideRepository(context))
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(repository) as T
            }

            else -> throw Throwable("unknown view model class: "+ modelClass.name)


        }
    }

}