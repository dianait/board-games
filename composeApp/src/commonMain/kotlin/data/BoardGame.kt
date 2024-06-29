package data

data class BoardGame(
    val gameId: Int,
    val name: String,
    val image: String,
    val minPlayers: Int,
    val maxPlayers: Int,
    val playingTime: Int,
    val yearPublished: Int,
    val userComment: String,
    val rating: Double
)

val boardGame1 = BoardGame(
    gameId = 1,
    name = "Catan",
    image = "https://example.com/images/catan.jpg",
    minPlayers = 3,
    maxPlayers = 4,
    playingTime = 120,
    yearPublished = 1995,
    userComment = "Uno de los mejores juegos de estrategia para jugar con amigos.",
    rating = 4.0
)

val boardGame2 = BoardGame(
    gameId = 2,
    name = "Terraforming Mars",
    image = "https://example.com/images/terraforming_mars.jpg",
    minPlayers = 1,
    maxPlayers = 5,
    playingTime = 180,
    yearPublished = 2016,
    userComment = "Gran juego de estrategia con mucha rejugabilidad y profundidad.",
    rating = 5.3
)

val boardGames = listOf(boardGame1, boardGame2)


