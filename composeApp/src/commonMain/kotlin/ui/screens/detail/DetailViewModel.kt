package ui.screens.home
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.BoardGame
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.BoardGameService
import data.RemoteBoardGame
import data.boardGame1
import kotlinx.coroutines.launch

class DetailViewModel(boardGameService: BoardGameService, gameId: Int): ViewModel() {
    var boardGame = boardGame1
    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            boardGame = boardGameService.getBoardGames().map {
                it.toDomainBoardGame()
            }.find { it.gameId == gameId } ?: boardGame1
            state =  UiState(isLoading = false, boardGame = boardGame)

        }
    }

    data class UiState(
        val boardGame: BoardGame = boardGame1,
        val isLoading: Boolean = false
    )

}

private fun RemoteBoardGame.toDomainBoardGame(): BoardGame {
    return BoardGame(
        gameId = gameId,
        name =  name,
        image = image,
        playingTime = playingTime,
        yearPublished = yearPublished,
        userComment = userComment,
        minPlayers = minPlayers,
        maxPlayers = maxPlayers
    )
}
