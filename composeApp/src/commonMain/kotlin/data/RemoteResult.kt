package data
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class RemoteBoardGame(
    val gameId: Int,
    val name: String,
    val image: String,
    val thumbnail: String,
    val minPlayers: Int,
    val maxPlayers: Int,
    val playingTime: Int,
    val isExpansion: Boolean,
    val yearPublished: Int,
    val bggRating: Double,
    val averageRating: Double,
    val rank: Int,
    val numPlays: Int,
    val rating: Double,
    val owned: Boolean,
    val preOrdered: Boolean,
    val forTrade: Boolean,
    val previousOwned: Boolean,
    val want: Boolean,
    val wantToPlay: Boolean,
    val wantToBuy: Boolean,
    val wishList: Boolean,
    val userComment: String
)
