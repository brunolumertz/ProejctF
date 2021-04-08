package lul.myapplication.dao

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class FMigration {
    companion object{
        val migration_1_2 = object :Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE 'Filme_Novo' " +
                        "('id' INT PRIMARY KEY, " +
                        "'tittle' TEXT NOT NULL, " +
                        "'poster' TEXT NOT NULL, " +
                        "'release' TEXT NOT NULL)"
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
        val migration_2_3 = object : Migration(2, 3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Filme ADD COLUMN status INTEGER")
            }
        }
    }
}

