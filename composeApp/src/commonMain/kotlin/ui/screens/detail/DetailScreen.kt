package ui.screens.detail
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
    val ratingDouble = game.rating
    val rating = if (ratingDouble % 1 == 0.0) {
        ratingDouble.toInt().toString()
    } else {
        ratingDouble.toString()
    }
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
                property("\uD83D\uDCC6 year published:", game.yearPublished)
                property("\uD83D\uDE0A players:", "$game.minPlayers-$game.maxPlayers")
                property("\uD83D\uDD63 time:", game.playingTime)
                property("✨\uFE0F:", game.rating.toString())
                if (game.userComment.isNotEmpty()) {
                    property("\uD83C\uDF99\uFE0F comment", game.userComment)
                }
            }
        )
    }
}

private fun AnnotatedString.Builder.property(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append(name)
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
