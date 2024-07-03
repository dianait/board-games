package data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import data.BoardGame

interface DB {
    fun clearAllTables()
}

@Database(entities = [BoardGame::class], version = 1)
abstract class BoardGamesDataBase: RoomDatabase(), DB {
    abstract fun boardGameDao(): BoardGamesDao
    override fun clearAllTables() {}
}
