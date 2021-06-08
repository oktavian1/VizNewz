package com.example.viznews.data.network

import android.util.Log
import com.example.viznews.data.network.remote.ApiServices
import com.example.viznews.data.network.remote.Services
import com.example.viznews.data.network.response.*
import com.example.viznews.utils.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val services: Services) {
    private val nameClass = RemoteDataSource::class.java.simpleName

    fun getOverallSentiment(time: Int, idCategory: Int? = null, callback: LoadGetOverallSentimentCallBack){
        val services = services.getOverall(time, idCategory)
        callback.onLoad(ApiResponse.Loading)

        services.enqueue(object: Callback<OverallSentimentResponse>{
            override fun onResponse(
                call: Call<OverallSentimentResponse>,
                response: Response<OverallSentimentResponse>
            ) {
                val body = response.body()
                if (body != null){
                    if (response.isSuccessful){
                        callback.onLoad(ApiResponse.Success(body.data))
                    }else{
                        callback.onLoad(ApiResponse.Error(body.message.toString()))
                        Log.e(nameClass, body.status.toString())
                    }
                }

            }

            override fun onFailure(call: Call<OverallSentimentResponse>, t: Throwable) {
                Log.e(nameClass, t.message.toString())
            }
        })
    }

    fun getOverallChart(time: Int, idCategory: Int? = null, callback: LoadGetOverallChartCallBack){
        val services = services.getChart(time, idCategory)
        callback.onLoad(ApiResponse.Loading)

        services.enqueue(object: Callback<OverallChartResponse>{
            override fun onResponse(
                    call: Call<OverallChartResponse>,
                    response: Response<OverallChartResponse>
            ) {
                val body = response.body()
                if (body != null){
                    if (response.isSuccessful){
                        callback.onLoad(ApiResponse.Success(body.data))
                    }else{
                        callback.onLoad(ApiResponse.Error(body.message.toString()))
                        Log.e(nameClass, body.status.toString())
                    }
                }

            }

            override fun onFailure(call: Call<OverallChartResponse>, t: Throwable) {
                Log.e(nameClass, t.message.toString())
            }
        })
    }

    fun getNews(time: Int, idCategory: Int? = null, callback: LoadGetNewsCallBack){
        val services = services.getNews(time, idCategory)
        callback.onLoad(ApiResponse.Loading)

        services.enqueue(object: Callback<NewsResponse>{
            override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
            ) {
                val body = response.body()
                Log.d("jambu1", body?.data.toString())
                if (body != null){
                    if (response.isSuccessful){
                        callback.onLoad(ApiResponse.Success(body.data))
                    }else{
                        callback.onLoad(ApiResponse.Error(body.message.toString()))
                        Log.e(nameClass, body.status.toString())
                    }
                }

            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e(nameClass, t.message.toString())
            }
        })
    }

    fun getWords(time: Int, idCategory: Int? = null, callback: LoadGetWordsCallBack){
        val services = services.getWords(time, idCategory)
        callback.onLoad(ApiResponse.Loading)

        services.enqueue(object: Callback<WordsResponse>{
            override fun onResponse(
                    call: Call<WordsResponse>,
                    response: Response<WordsResponse>
            ) {
                val body = response.body()
                if (body != null){
                    if (response.isSuccessful){
                        callback.onLoad(ApiResponse.Success(body.data))
                    }else{
                        callback.onLoad(ApiResponse.Error(body.message.toString()))
                        Log.e(nameClass, body.status.toString())
                    }
                }

            }

            override fun onFailure(call: Call<WordsResponse>, t: Throwable) {
                Log.e(nameClass, t.message.toString())
            }
        })
    }


    interface LoadGetOverallSentimentCallBack{
        fun onLoad(res: ApiResponse<DataOverallSentiment>)
    }

    interface LoadGetOverallChartCallBack{
        fun onLoad(res: ApiResponse<List<DataOverallChart>>)
    }

    interface LoadGetNewsCallBack{
        fun onLoad(res: ApiResponse<List<DataNewsResponse>>)
    }

    interface LoadGetWordsCallBack{
        fun onLoad(res: ApiResponse<DataWords>)
    }

    companion object {

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiServices: ApiServices): RemoteDataSource =
            instance ?: synchronized(this){
                RemoteDataSource(apiServices.create()).apply { instance = this }
            }
    }
}