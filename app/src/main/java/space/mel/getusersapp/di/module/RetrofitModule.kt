package space.mel.getusersapp.di.module

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.mel.getusersapp.constants.BaseURL
import space.mel.getusersapp.retrofit.UserApi

val retrofitModule = module { // module for provide all dependencies
    single { provideRetrofit() } //it creates a singleton Retrofit that can be used across the app as a singular instance
    single { provideUserApi(get()) } //it creates a singleton UserApi
}

fun provideRetrofit(): Retrofit { //fun return Retrofit
    return Retrofit.Builder()
        .baseUrl(BaseURL.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideUserApi(retrofit: Retrofit): UserApi { //fun take input Retrofit from above fun and create UserApi
    return retrofit.create(UserApi::class.java)
}