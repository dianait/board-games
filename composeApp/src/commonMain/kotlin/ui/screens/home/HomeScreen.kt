package ui.screens.home
import data.BoardGame
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import boardgames.composeapp.generated.resources.Res
import boardgames.composeapp.generated.resources.app_name
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource
import ui.screens.Screen
import ui.screens.common.Loading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGameClick: (BoardGame) -> Unit,
    viewModel: HomeViewModel
) {
    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(Res.string.app_name)) },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            val state = viewModel.state
            Loading(enabled = state.isLoading, modifier = Modifier)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(padding)
            ) {
                items(state.boardGames, key = { it.gameId }) {
                    BoardGameItem(boardgame = it, onGameClick = { onGameClick(it) })
                }
            }
        }

    }
}

@Composable
fun BoardGameItem(boardgame: BoardGame, onGameClick: () -> Unit) {
    Column (modifier = Modifier.clickable(onClick = onGameClick))
    {
        Image(boardgame = boardgame)
        Text(
            text = boardgame.name,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun Image(boardgame: BoardGame) {
    AsyncImage(
        model = boardgame.image,
        contentDescription = boardgame.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2/3f)
            .clip(MaterialTheme.shapes.small))
}
