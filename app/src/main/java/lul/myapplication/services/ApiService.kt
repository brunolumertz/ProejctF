package lul.myapplication.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org"

class ApiService {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val filmeService: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }

}
