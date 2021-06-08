package com.example.viznews.data.network.response

import com.google.gson.annotations.SerializedName

data class OverallChartResponse(

	@field:SerializedName("data")
	val data: List<DataOverallChart>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataOverallChart(

	@field:SerializedName("negative")
	val negative: Int,

	@field:SerializedName("neutral")
	val neutral: Int,

	@field:SerializedName("positive")
	val positive: Int
)
