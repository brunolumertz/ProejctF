package lul.myapplication.repository

import lul.myapplication.dao.FilmesDAO
import lul.myapplication.models.Filme
import lul.myapplication.models.FilmeDetalhes
import lul.myapplication.models.FilmeResponse
import lul.myapplication.services.ApiInterface
import lul.myapplication.services.ApiService
import retrofit2.Response

class FilmesRepository (

//    private val dao: FilmesDAO,
    private val api: ApiInterface = ApiService().filmeService
)   {
    suspend fun getFilmesPopulares()
            :Response<FilmeResponse>{
        return api.getListaFilme()
    }

    suspend fun getFilmeDetalhes(id: Int):Response<FilmeDetalhes>{
        return api.getDetalhes(id)
    }
}