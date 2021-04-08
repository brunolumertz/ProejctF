package lul.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import kotlinx.coroutines.flow.Flow
import lul.myapplication.models.Filme
import androidx.room.Query

@Dao
interface FilmesDAO {

    @Insert(onConflict = REPLACE)
    suspend fun salvaListaJaVi(filme: Filme)

    @Insert(onConflict = REPLACE)
    suspend fun salvaListaQueroVer(filme: Filme)

    @Query("SELECT * FROM Filme WHERE status = 1")
    fun buscaJaVi(): Flow<List<Filme>>

    @Query("SELECT * FROM Filme WHERE status = 2")
    fun buscaQueroVer(): Flow<List<Filme>>

    @Delete
    suspend fun deletaJaVi(filme: Filme)

    @Delete
    suspend fun deletaQueroVer(filme: Filme)
}