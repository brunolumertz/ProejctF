package lul.myapplication.repository

import androidx.room.Dao
import lul.myapplication.dao.FilmesDAO
import lul.myapplication.models.FilmeResponse
import lul.myapplication.services.ApiInterface
import lul.myapplication.services.ApiService
import retrofit2.Response

class FilmesRepository (

    private val dao: FilmesDAO,
    private val api: ApiInterface = ApiService().filmeService
)   {
    suspend fun getFilmesPopulares()
            :Response<FilmeResponse>{
        return api.getListaFilme()
    }
}