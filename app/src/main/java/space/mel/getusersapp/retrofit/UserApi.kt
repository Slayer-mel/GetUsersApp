package space.mel.getusersapp.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import space.mel.getusersapp.data.Results

interface UserApi {

    @GET("/api")
    suspend fun getUsers(
        @Query("results") count: Int,
        @Query("gender") gender: String,
    ): Results
}