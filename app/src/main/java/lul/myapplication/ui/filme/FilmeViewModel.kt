package lul.myapplication.ui.filme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lul.myapplication.models.FilmeResponse
import lul.myapplication.repository.FilmesRepository
import retrofit2.Response

class FilmeViewModel (
    private val filmesrepository: FilmesRepository
) : ViewModel(){

    val fResponse: MutableLiveData<Response<FilmeResponse>> = MutableLiveData()

    fun getFilmes(){
        viewModelScope.launch {
            val response = filmesrepository.getFilmesPopulares()
            fResponse.value = response
        }
    }
}