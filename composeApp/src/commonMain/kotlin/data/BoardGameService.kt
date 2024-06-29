package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BoardGameService(
    private val client: HttpClient,
    private var boardGamesCache: List<RemoteBoardGame> = emptyList()
) {

    suspend fun getBoardGames(): List<RemoteBoardGame> {
        if (boardGamesCache.isNotEmpty()) {
            return boardGamesCache
        }
        val data = client.get("/collection/dianait")
                .body<List<RemoteBoardGame>>()
        boardGamesCache = data
        return boardGamesCache
    }

    suspend fun getBoardGame(gameId: Int): RemoteBoardGame {
        val game = boardGamesCache.first { it.gameId == gameId }
        return game
    }
}
