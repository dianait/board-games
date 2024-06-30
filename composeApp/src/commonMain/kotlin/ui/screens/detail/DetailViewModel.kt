package ui.screens.home
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.BoardGame
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.BoardGamesRepository
import kotlinx.coroutines.launch

class DetailViewModel(repository: BoardGamesRepository, gameId: Int): ViewModel() {
    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            state =  UiState(isLoading = false, boardGame = repository.fetchBoardGame(gameId))
        }
    }

    data class UiState(
        val boardGame: BoardGame? = null,
        val isLoading: Boolean = false
    )

}
