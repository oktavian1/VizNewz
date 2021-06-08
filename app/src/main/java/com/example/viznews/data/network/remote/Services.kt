package com.example.viznews.data.network.remote

import com.example.viznews.data.network.response.NewsResponse
import com.example.viznews.data.network.response.OverallChartResponse
import com.example.viznews.data.network.response.OverallSentimentResponse
import com.example.viznews.data.network.response.WordsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Services{

    @GET("api/overall")
    fun getOverall(@Query("time") time: Int, @Query("id_category") idCategory: Int? = null): Call<OverallSentimentResponse>

    @GET("api/overall/chart")
    fun getChart(@Query("time") time: Int, @Query("id_category") idCategory: Int? = null): Call<OverallChartResponse>

    @GET("api/news")
    fun getNews(@Query("time") time: Int, @Query("id_category") idCategory: Int? = null): Call<NewsResponse>

    @GET("api/words")
    fun getWords(@Query("time") time: Int, @Query("id_category") idCategory: Int? = null): Call<WordsResponse>

}