package lul.myapplication

import android.app.Application
import androidx.room.Database
import lul.myapplication.dao.DataBase
import lul.myapplication.repository.FilmesRepository

class MyApplication: Application() {

    val database by lazy { DataBase.getDataBase(this) }
    val repository by lazy { FilmesRepository(database.filmeDAO()) }

}
