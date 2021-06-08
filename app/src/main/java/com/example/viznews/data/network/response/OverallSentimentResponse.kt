package com.example.viznews.data.network.response

import com.google.gson.annotations.SerializedName

data class OverallSentimentResponse(

	@field:SerializedName("data")
	val data: DataOverallSentiment,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataOverallSentiment(

	@field:SerializedName("negative")
	val negative: Int,

	@field:SerializedName("neutral")
	val neutral: Int,

	@field:SerializedName("positive")
	val positive: Int
)
