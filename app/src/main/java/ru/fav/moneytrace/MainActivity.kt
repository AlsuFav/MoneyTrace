package ru.fav.moneytrace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ru.fav.moneytrace.base.theme.MTTheme
import ru.fav.moneytrace.navigation.NavigationManager
import javax.inject.Inject
import androidx.compose.runtime.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MTTheme {
                var showLottieAnimation by remember { mutableStateOf(true) }

                if (showLottieAnimation) {
                    SplashScreen(
                        onAnimationFinished = {
                            showLottieAnimation = false
                        }
                    )
                } else {
                    MainScreen(navigationManager)
                }
            }
        }
    }
}

