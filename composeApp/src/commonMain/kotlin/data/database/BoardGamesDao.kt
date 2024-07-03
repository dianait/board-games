package data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.BoardGame
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardGamesDao {

@Query("SELECT * FROM BoardGame")
fun fetchBoardGames(): Flow<List<BoardGame>>

@Query("SELECT * FROM BoardGame WHERE gameId = :gameId")
fun fetchBoardGameById(gameId: Int): Flow<BoardGame>

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun save(games: List<BoardGame>)
}
