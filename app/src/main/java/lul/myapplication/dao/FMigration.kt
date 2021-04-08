package lul.myapplication.dao

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class FMigration {
    companion object{
        val migration_1_2 = object :Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE 'Filme_Novo' " +
                        "('id' INTEGER PRIMARY KEY, " +
                        "'tittle' TEXT, " +
                        "'poster' TEXT, " +
                        "'release' TEXT)"
                )
                database.execSQL("INSERT INTO Filme_Novo (" +
                        "id, tittle, poster, release) " +
                        "SELECT id, tittle, poster, release " +
                        "FROM Filme"
                )
                database.execSQL("DROP TABLE Filme")
                database.execSQL("ALTER TABLE Filme_Novo RENAME TO Filme")
            }
        }
    }
}

