package lul.myapplication.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import lul.myapplication.models.Filme

@Database(entities = [Filme::class], version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract fun filmeDAO(): FilmesDAO
    companion object{
        @Volatile
        private var INSTANCE: DataBase? = null
        fun getDataBase(context: Context): DataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "filme-db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}