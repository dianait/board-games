package ui.screens.home
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.BoardGame
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.BoardGameService
import data.BoardGamesRepository
import data.RemoteBoardGame
import kotlinx.coroutines.launch

class HomeViewModel(
    repository: BoardGamesRepository
): ViewModel() {
    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            try {
                val boardGames = repository.fetchBoardGames()
                state = UiState(isLoading = false, boardGames = boardGames)
            }
            catch (e: Exception) {
                state = UiState(
                    isLoading = false,
                    error = "Error al cargar los juegos: ${e.message}"
                )
            }
        }
    }

    data class UiState(
        val boardGames: List<BoardGame> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

}


