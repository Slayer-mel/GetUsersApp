package space.mel.getusersapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.mel.getusersapp.constants.BaseURL

object UserNetwork {
    val retrofit by lazy {
 Retrofit.Builder()
     .baseUrl(BaseURL.BASE_URL)
     .addConverterFactory(GsonConverterFactory.create())
     .build()
     .create(UserApi::class.java)
    }
}