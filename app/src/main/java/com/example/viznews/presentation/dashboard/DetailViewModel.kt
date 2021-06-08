package com.example.viznews.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.viznews.data.NewsRepository
import com.example.viznews.data.model.OverallSentiment
import com.example.viznews.data.network.response.DataOverallChart
import com.example.viznews.data.network.response.DataWords
import com.example.viznews.utils.Resource
import kotlin.properties.Delegates

class DetailViewModel(private val repository: NewsRepository): ViewModel() {

    private var time by Delegates.notNull<Int>()
    private var idCategory by Delegates.notNull<Int>()

    fun queryOverallSentiment(time: Int, idCategory: Int?= null) {
        this.time = time
        this.idCategory = idCategory!!
    }

    fun getOverallSentiment(): LiveData<Resource<OverallSentiment>> =
        repository.getOverallSentiment(time, idCategory)

    fun getOverallChart(): LiveData<Resource<List<DataOverallChart>>> =
            repository.getOverallChart(time, idCategory)


    fun getWords(): LiveData<Resource<DataWords>> =
            repository.getWords(time, idCategory)

}