package com.example.viznews.presentation.dashboard

import android.util.Log
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
    private var idCategory: Int? = null
    private var state by Delegates.notNull<Int>()

    fun queryOverallSentiment(time: Int, idCategory: Int? = null, state: Int) {
        this.time = time
        this.idCategory = idCategory
        this.state = state
    }

    fun getOverallSentiment(): LiveData<Resource<OverallSentiment>> {
        return if (state == 1){
            repository.getOverallSentiment(time)
        }else {
            repository.getOverallSentiment(time, idCategory)
        }
    }

    fun getOverallChart(): LiveData<Resource<List<DataOverallChart>>> {
        return if(state == 1){
            repository.getOverallChart(time)
        }else{
            repository.getOverallChart(time, idCategory)
        }
    }

    fun getWords(): LiveData<Resource<DataWords>> {
        return if(state == 1){
            repository.getWords(time)
        }else{
            repository.getWords(time, idCategory)
        }
    }


}