package lul.myapplication.ui.detalhes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lul.myapplication.models.Filme
import lul.myapplication.models.FilmeDetalhes
import lul.myapplication.repository.FilmesRepository
import retrofit2.Response

class DetalhesFilmeViewModel (
    private val filmesrepository: FilmesRepository
) : ViewModel(){

    val fResponse: MutableLiveData<Response<FilmeDetalhes>> = MutableLiveData()

    fun getDetalhes(id: Int){
        viewModelScope.launch {
            val response = filmesrepository.getFilmeDetalhes(id)
            fResponse.value = response
        }
    }

    fun salvaListaJaVi(filme: Filme) {
        viewModelScope.launch {
            filmesrepository.salvaListaJaVi(filme)
        }
    }

    fun salvaListaQueroVer(filme: Filme) {
        viewModelScope.launch {
            filmesrepository.salvaListaQueroVer(filme)
        }
    }
}