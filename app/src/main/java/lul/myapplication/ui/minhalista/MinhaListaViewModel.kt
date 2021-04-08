package lul.myapplication.ui.minhalista

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lul.myapplication.models.Filme
import lul.myapplication.repository.FilmesRepository

class MinhaListaViewModel(private val repository: FilmesRepository) : ViewModel() {

    val filmesJaVi: LiveData<List<Filme>> = repository.filmesJaVi.asLiveData()
    val filmesQueroVer: LiveData<List<Filme>> = repository.filmesQueroVer.asLiveData()

    fun deletaFilmeJaVi(filme: Filme){
        viewModelScope.launch {
            repository.deletaListaJaVi(filme)//puxar do repositorio dps
        }
    }

    fun deletaFilmeQueroVer(filme: Filme){
        viewModelScope.launch{
            repository.deletaListaQueroVer(filme)
        }
    }

    fun salvaListaJavi(filme: Filme) {
        viewModelScope.launch {
            repository.salvaListaJaVi(filme)
        }
    }

    fun salvaListaQueroVer(filme: Filme) {
        viewModelScope.launch {
            repository.salvaListaQueroVer(filme)
        }
    }
}