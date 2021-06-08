package com.example.viznews.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.viznews.data.model.OverallSentiment
import com.example.viznews.data.network.RemoteDataSource
import com.example.viznews.data.network.response.DataNewsResponse
import com.example.viznews.data.network.response.DataOverallChart
import com.example.viznews.data.network.response.DataOverallSentiment
import com.example.viznews.data.network.response.DataWords
import com.example.viznews.utils.ApiResponse
import com.example.viznews.utils.DataMapper.mapToOverall
import com.example.viznews.utils.Resource

class NewsRepository(private val remoteDataSource: RemoteDataSource): NewsDataSource {

    override fun getOverallSentiment(
        time: Int,
        idCategory: Int?
    ): LiveData<Resource<OverallSentiment>> {
        val result = MutableLiveData<Resource<OverallSentiment>>()

        remoteDataSource.getOverallSentiment(time, idCategory, object: RemoteDataSource.LoadGetOverallSentimentCallBack{
            override fun onLoad(res: ApiResponse<DataOverallSentiment>) {
                when(res){
                    is ApiResponse.Success -> {
                        val mapper = mapToOverall(res.data)
                        result.postValue(Resource.Success(mapper))
                    }
                    is ApiResponse.Error -> {
                        result.postValue(Resource.Loading())
                        result.postValue(Resource.Error(res.errorMessage))
                    }
                    is ApiResponse.Empty -> {
                        result.postValue(Resource.Loading())
                    }
                    is ApiResponse.Loading -> {
                        result.postValue(Resource.Loading())
                    }
                }
            }
        })

        return result
    }

    override fun getOverallChart(time: Int, idCategory: Int?): LiveData<Resource<List<DataOverallChart>>> {
        val result = MutableLiveData<Resource<List<DataOverallChart>>>()

        remoteDataSource.getOverallChart(time, idCategory, object: RemoteDataSource.LoadGetOverallChartCallBack{
            override fun onLoad(res: ApiResponse<List<DataOverallChart>>) {
                when(res){
                    is ApiResponse.Success -> {
                        result.postValue(Resource.Success(res.data))
                    }
                    is ApiResponse.Error -> {
                        result.postValue(Resource.Loading())
                        result.postValue(Resource.Error(res.errorMessage))
                    }
                    is ApiResponse.Empty -> {
                        result.postValue(Resource.Loading())
                    }
                    is ApiResponse.Loading -> {
                        result.postValue(Resource.Loading())
                    }
                }
            }
        })

        return result
    }

    override fun getNews(time: Int, idCategory: Int?): LiveData<Resource<List<DataNewsResponse>>> {
        val result = MutableLiveData<Resource<List<DataNewsResponse>>>()

        remoteDataSource.getNews(time, idCategory, object: RemoteDataSource.LoadGetNewsCallBack{
            override fun onLoad(res: ApiResponse<List<DataNewsResponse>>) {
                when(res){
                    is ApiResponse.Success -> {
                        Log.d("jambu1", res.data.toString())
                        result.postValue(Resource.Success(res.data))
                    }
                    is ApiResponse.Error -> {
                        result.postValue(Resource.Loading())
                        result.postValue(Resource.Error(res.errorMessage))
                    }
                    is ApiResponse.Empty -> {
                        result.postValue(Resource.Loading())
                    }
                    is ApiResponse.Loading -> {
                        result.postValue(Resource.Loading())
                    }
                }
            }
        })

        return result
    }

    override fun getWords(time: Int, idCategory: Int?): LiveData<Resource<DataWords>> {
        val result = MutableLiveData<Resource<DataWords>>()

        remoteDataSource.getWords(time, idCategory, object: RemoteDataSource.LoadGetWordsCallBack{
            override fun onLoad(res: ApiResponse<DataWords>) {
                when(res){
                    is ApiResponse.Success -> {
                        result.postValue(Resource.Success(res.data))
                    }
                    is ApiResponse.Error -> {
                        result.postValue(Resource.Loading())
                        result.postValue(Resource.Error(res.errorMessage))
                    }
                    is ApiResponse.Empty -> {
                        result.postValue(Resource.Loading())
                    }
                    is ApiResponse.Loading -> {
                        result.postValue(Resource.Loading())
                    }
                }
            }
        })

        return result
    }

    companion object{
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): NewsRepository =
            instance ?: synchronized(this){
                NewsRepository(remoteDataSource).apply { instance = this }
            }
    }
}