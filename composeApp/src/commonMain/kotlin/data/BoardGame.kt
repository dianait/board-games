package data

data class BoardGame(
    val gameId: Int,
    val name: String,
    val image: String,
    val minPlayers: String,
    val maxPlayers: String,
    val playingTime: String,
    val yearPublished: String,
    val userComment: String,
    val rating: Double
)


