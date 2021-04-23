package lul.myapplication.util

import android.app.Application
import lul.myapplication.dao.DataBase
import lul.myapplication.repository.FilmesRepository

class MyApplication : Application() {

    val database by lazy { DataBase.getDatabase(this) }
    val repository by lazy { FilmesRepository(database.filmeDAO()) }

}
