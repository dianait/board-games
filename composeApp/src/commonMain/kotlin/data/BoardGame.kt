package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BoardGame(
    @PrimaryKey(autoGenerate = true)
    val gameId: Int,
    val name: String,
    val image: String,
    val minPlayers: String,
    val maxPlayers: String,
    val playingTime: String,
    val yearPublished: String,
    val userComment: String,
    val rating: Number
)


