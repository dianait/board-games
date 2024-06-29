package ui.screens.detail
import androidx.compose.foundation.clickable
import data.BoardGame
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import boardgames.composeapp.generated.resources.Res
import boardgames.composeapp.generated.resources.back
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource
import ui.screens.Screen
import ui.screens.common.Loading
import ui.screens.home.DetailViewModel
import ui.screens.home.Image

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: DetailViewModel, onBack: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(viewModel.boardGame.name) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                           Icon(
                               imageVector = Icons.AutoMirrored.Default.ArrowBack,
                               contentDescription = stringResource(Res.string.back)
                           )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            val maxPlayers = viewModel.boardGame.maxPlayers.toString()
            val minPlayers = viewModel.boardGame.minPlayers.toString()
            val playTime = viewModel.boardGame.playingTime.toString()
            val yearPublished = viewModel.boardGame.yearPublished.toString()
            val comment = viewModel.boardGame.userComment
            val ratingDouble = viewModel.boardGame.rating
            val rating = if (ratingDouble % 1 == 0.0) {
                ratingDouble.toInt().toString()
            } else {
                ratingDouble.toString()
            }
            val state = viewModel.state
            Loading(enabled = state.isLoading, modifier = Modifier)
            Column(modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())

                ) {
                AsyncImage(
                    model = viewModel.boardGame.image,
                    contentDescription = viewModel.boardGame.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = viewModel.boardGame.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Text(text = "📆 published in $yearPublished")
                Text(text = "😊 $minPlayers-$maxPlayers players")
                Text(text = "🕣 time: $playTime min")
                Text(text = "✨️ $rating")
                if (comment.isNotEmpty()) {
                    Text(text = "🎙️ $comment")
                }

            }
        }
    }
}
