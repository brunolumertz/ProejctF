package lul.myapplication.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "41d817e2d205137880ce5f1b63f205dc"

class ApiService {

    private val client = OkHttpClient
        .Builder()
        .addInterceptor(Intercept)
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

    object Intercept : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val url = originalRequest
                .url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            val request = originalRequest
                .newBuilder()
                .url(url)
                .build()
            return chain.proceed(request)
        }
    }

}