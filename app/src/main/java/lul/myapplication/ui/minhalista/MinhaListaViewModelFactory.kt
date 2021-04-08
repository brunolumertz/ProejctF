package lul.myapplication.ui.minhalista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lul.myapplication.repository.FilmesRepository

class MinhaListaViewModelFactory(private val repository: FilmesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MinhaListaViewModel(repository) as T
    }

}