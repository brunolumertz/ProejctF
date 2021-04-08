package lul.myapplication.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import lul.myapplication.dao.FMigration.Companion.migration_1_2
import lul.myapplication.dao.FMigration.Companion.migration_2_3
import lul.myapplication.models.Filme

@Database(entities = [Filme::class], version = 3, exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract fun filmeDAO(): FilmesDAO
    companion object{
        @Volatile
        private var INSTANCE: DataBase? = null
        fun getDatabase(context: Context): DataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "filme-db")
                    .addMigrations(migration_1_2, migration_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}