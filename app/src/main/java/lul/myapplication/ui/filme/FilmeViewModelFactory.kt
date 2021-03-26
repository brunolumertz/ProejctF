package lul.myapplication.ui.filme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lul.myapplication.repository.FilmesRepository

class FilmeViewModelFactory(
    private val filmesrepository : FilmesRepository
)   : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FilmeViewModel(filmesrepository) as T
    }
}
