package ui.screens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import data.BoardGamesRepository
import data.BoardGameService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ui.screens.detail.DetailScreen
import ui.screens.home.DetailViewModel
import ui.screens.home.HomeScreen
import ui.screens.home.HomeViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val repository = RememberBoardGamesRepository()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onGameClick = { boardGame ->
                navController.navigate("detail/${boardGame.gameId}")
            },
                viewModel = viewModel { HomeViewModel(repository) }
            )
        }
        composable("detail/{boardId}",
            arguments = listOf(navArgument("boardId") { type = NavType.IntType })
        ) { backStackEntry ->
            val boardId = checkNotNull(backStackEntry.arguments?.getInt("boardId"))
            DetailScreen(
                viewModel { DetailViewModel(repository, boardId) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun RememberBoardGamesRepository(): BoardGamesRepository {
    val client =
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true

                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "bgg-json.azurewebsites.net"
                }
            }
        }

    val service = BoardGameService(client)
    val repository = BoardGamesRepository(service)
    return remember { repository }
}
