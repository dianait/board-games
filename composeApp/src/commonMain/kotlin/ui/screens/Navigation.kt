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
import data.boardGames
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


    val client = remember {
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
    }

    val service = BoardGameService(client)
    val repository = BoardGamesRepository(service)

    val viewModel = viewModel {
        HomeViewModel(repository)
    }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onGameClick = { boardGame ->
                navController.navigate("detail/${boardGame.gameId}")
            },
                viewModel = viewModel
            )
        }
        composable("detail/{boardId}",
            arguments = listOf(navArgument("boardId") { type = NavType.IntType })
        ) { backStackEntry ->
            val boardId = backStackEntry.arguments?.getInt("boardId") ?: 0
            val detailViewModel = viewModel {
                DetailViewModel(
                    repository = repository,
                    gameId = boardId
                )
            }

            DetailScreen(viewModel = detailViewModel, onBack = { navController.popBackStack() })
        }
    }
}
