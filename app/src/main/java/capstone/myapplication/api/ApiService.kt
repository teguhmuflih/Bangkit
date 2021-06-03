package capstone.myapplication.api

import capstone.myapplication.ResultItem
import capstone.myapplication.SolutionResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("detail/{label}")
    fun getRestaurant(
        @Path("label") label: String
    ): Call<SolutionResponse>


    fun postReview(
        @Field("id") treatment: String,
        @Field("name") id: String,
        @Field("review") label: String
    ): Call<ResultItem>
}