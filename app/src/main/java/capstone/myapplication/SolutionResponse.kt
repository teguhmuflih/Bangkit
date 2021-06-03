package capstone.myapplication

import com.google.gson.annotations.SerializedName

data class SolutionResponse(

	@field:SerializedName("result")
	val result: List<ResultItem>
)

data class ResultItem(

	@field:SerializedName("treatment")
	val treatment: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("label")
	val label: String
)
