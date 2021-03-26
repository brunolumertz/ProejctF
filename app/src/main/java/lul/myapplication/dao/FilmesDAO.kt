package lul.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lul.myapplication.models.Filme

@Dao
interface FilmesDAO {

    @Query("SELECT * FROM Filme")
    fun buscaCompleta(): Flow<List<Filme>>

//    @Query("SELECT * FROM Filme WHERE id = :id")
//    fun buscaPorId(id: String): LiveData<Filme>
}