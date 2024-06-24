package ui.screens
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Screen(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()
    val colorSqueme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    MaterialTheme(colorSqueme) {
        Surface(modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}
