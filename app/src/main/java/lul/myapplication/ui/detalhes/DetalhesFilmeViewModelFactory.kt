package lul.myapplication.ui.detalhes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lul.myapplication.repository.FilmesRepository
import lul.myapplication.ui.filme.FilmeViewModel

class DetalhesFilmeViewModelFactory (
    private val filmesrepository : FilmesRepository
)   : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetalhesFilmeViewModel(filmesrepository) as T
    }
}