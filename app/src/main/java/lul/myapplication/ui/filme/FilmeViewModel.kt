package lul.myapplication.ui.filme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lul.myapplication.models.FilmeResponse
import lul.myapplication.repository.FilmesRepository
import lul.myapplication.ui.Resource
import retrofit2.Response

class FilmeViewModel (
    private val filmesrepository: FilmesRepository
) : ViewModel(){

    val fResponse: MutableLiveData<Response<FilmeResponse>> = MutableLiveData()
    val fPesquisaResponse: MutableLiveData<Resource<FilmeResponse>> = MutableLiveData()
    var fPesquisaResponseNew:FilmeResponse? = null

    fun getFilmes(){
        viewModelScope.launch {
            val response = filmesrepository.getFilmesPopulares()
            fResponse.value = response
        }
    }

//    fun getPesquisa(keyword: String) {
//        viewModelScope.launch {
//            val response = filmesrepository.getFilmesPesquisa(keyword)
//            fPesquisaResponse.value = response
//        }
//    }

    fun getSearchMovie(keyword: String) = viewModelScope.launch {
        searchMovie(keyword)
    }

    // Obtem os resultados da pesquisa feita
    private suspend fun searchMovie(searchQuery: String) {
        fPesquisaResponse.postValue(Resource.Loading())
        val response = filmesrepository.getSearchMovie(searchQuery)
        fPesquisaResponse.postValue(searchMovieResponse(response))
    }


    // Obtem os filmes através da pesquisa
    private fun searchMovieResponse(response: Response<FilmeResponse>): Resource<FilmeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (fPesquisaResponseNew == null) {
                    fPesquisaResponseNew = resultResponse
                }
                return Resource.Success(fPesquisaResponseNew ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}