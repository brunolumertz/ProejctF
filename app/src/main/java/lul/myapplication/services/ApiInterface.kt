package lul.myapplication.services

import lul.myapplication.models.Filme
import lul.myapplication.models.FilmeResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/3/movie/popular?api_key=41d817e2d205137880ce5f1b63f205dc")
    suspend fun getListaFilme(): Response<FilmeResponse>

    @GET("/3/movies/get-movie-details?api_key=41d817e2d205137880ce5f1b63f205dc")
    suspend fun getDetalhes(): Response<Filme>
}