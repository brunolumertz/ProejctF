package lul.myapplication.ui.detalhes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lul.myapplication.models.Filme
import lul.myapplication.models.FilmeResponse
import lul.myapplication.repository.FilmesRepository
import retrofit2.Response

class DetalhesFilmeViewModel (
    private val filmesrepository: FilmesRepository
) : ViewModel(){

    val fResponse: MutableLiveData<Response<Filme>> = MutableLiveData()

    fun getDetalhes(id: String){
        viewModelScope.launch {
            val response = filmesrepository.getFilmeDetalhes(id)
            fResponse.value = response
        }
    }
}