package com.example.viznews.data.network.response

import com.google.gson.annotations.SerializedName

data class WordsResponse(

		@field:SerializedName("data")
	val data: DataWords,

		@field:SerializedName("message")
	val message: String? = null,

		@field:SerializedName("status")
	val status: String? = null
)

data class NegativeItem(

	@field:SerializedName("word")
	val word: String? = null,

	@field:SerializedName("frequency")
	val frequency: Int? = null
)

data class DataWords(

	@field:SerializedName("negative")
	val negative: List<NegativeItem>,

	@field:SerializedName("positive")
	val positive: List<PositiveItem>
)

data class PositiveItem(

	@field:SerializedName("word")
	val word: String? = null,

	@field:SerializedName("frequency")
	val frequency: Int? = null
)
