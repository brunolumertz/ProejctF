package lul.myapplication.repository

import kotlinx.coroutines.flow.Flow
import lul.myapplication.dao.FilmesDAO
import lul.myapplication.models.Filme
import lul.myapplication.models.FilmeDetalhes
import lul.myapplication.models.FilmeResponse
import lul.myapplication.services.ApiInterface
import lul.myapplication.services.ApiService
import retrofit2.Response

class FilmesRepository (

    private val dao: FilmesDAO,
    private val api: ApiInterface = ApiService().filmeService
) {

    val filmesJaVi: Flow<List<Filme>> = dao.buscaJaVi()
    val filmesQueroVer: Flow<List<Filme>> = dao.buscaQueroVer()

    suspend fun getFilmesPopulares()
            :Response<FilmeResponse>{
        return api.getListaFilme()
    }

    suspend fun getFilmeDetalhes(id: Int):Response<FilmeDetalhes>{
        return api.getDetalhes(id)
    }

    //deleta filme da lista Ja Vi
    suspend fun deletaListaJaVi(filme: Filme){
        dao.deletaJaVi(filme)
    }

    suspend fun deletaListaQueroVer(filme: Filme){
        dao.deletaQueroVer(filme)
    }

    suspend fun salvaListaJaVi(filme: Filme){
        filme.status = 1
        dao.salvaListaJaVi(filme)
    }

    suspend fun salvaListaQueroVer(filme: Filme){
        filme.status = 2
        dao.salvaListaQueroVer(filme)
    }

    suspend fun getFilmesPesquisa(keyword : String) : Response<FilmeResponse>{
        return api.getSearchMovie(keyword)
    }

    // Busca os filmes pesquisados
    suspend fun getSearchMovie(keyword: String): Response<FilmeResponse> {
        return api.getSearchMovie(keyword)
    }

}