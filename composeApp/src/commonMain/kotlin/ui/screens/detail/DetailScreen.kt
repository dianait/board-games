package ui.screens.detail
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import data.BoardGame
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import boardgames.composeapp.generated.resources.Res
import boardgames.composeapp.generated.resources.back
import boardgames.composeapp.generated.resources.comment
import boardgames.composeapp.generated.resources.players
import boardgames.composeapp.generated.resources.playingTime
import boardgames.composeapp.generated.resources.rating
import boardgames.composeapp.generated.resources.yearPublished
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
    val state = viewModel.state
    Screen {
        Scaffold(
            topBar = { state.boardGame?.name?.let { TopBar(it, onBack, scrollBehavior) } },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            Loading(enabled = state.isLoading, modifier = Modifier.padding(padding))
            state.boardGame.let { boardGame ->
                boardGame?.let { BoardGameDetail(it, modifier = Modifier.padding(padding)) }
            }
        }
    }
}

@Composable
private fun BoardGameDetail(
    game: BoardGame,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())

    ) {
        AsyncImage(
            model = game.image,
            contentDescription = game.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Text(
            text = game.name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = buildAnnotatedString {
                property(stringResource(Res.string.yearPublished), game.yearPublished)
                property(stringResource(Res.string.players), "${game.minPlayers} - ${game.maxPlayers}")
                property(stringResource(Res.string.playingTime), "${game.playingTime} min")
                property(stringResource(Res.string.rating), game.rating.toString(), end = game.userComment.isEmpty())
                if (game.userComment.isNotEmpty()) {
                    property(stringResource(Res.string.comment), game.userComment, end = true)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
    }
}

private fun AnnotatedString.Builder.property(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (!end) {
            append("\n")
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    title: String,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = { Text(title) },
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
}
