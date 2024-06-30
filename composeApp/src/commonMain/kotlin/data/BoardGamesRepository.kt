package data

class BoardGamesRepository(private val boardGamesService: BoardGameService) {

    suspend fun fetchBoardGames(): List<BoardGame> {
        return boardGamesService.getBoardGames().map { it.toDomainBoardGame() }
    }

    suspend fun fetchBoardGame(gameId: Int): BoardGame {
        return boardGamesService.getBoardGame(gameId).toDomainBoardGame()
    }

    private fun RemoteBoardGame.toDomainBoardGame(): BoardGame {
        return BoardGame(
            gameId = gameId,
            name = name,
            image = image,
            minPlayers = minPlayers.toString(),
            maxPlayers = maxPlayers.toString(),
            playingTime = playingTime.toString(),
            yearPublished = yearPublished.toString(),
            userComment = userComment,
            rating = rating
        )
    }
}
