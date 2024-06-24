package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BoardGameService(
    private val client: HttpClient
) {
    suspend fun getBoardGames(): List<RemoteBoardGame> {
        return client.get("https://bgg-json.azurewebsites.net/collection/dianait")
                .body<List<RemoteBoardGame>>()
    }
}
