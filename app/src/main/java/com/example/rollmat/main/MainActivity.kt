package com.example.rollmat.main


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rollmat.login.LoginScreen
import com.example.rollmat.profile.ui.ProfileScreen
import com.example.rollmat.profile.ui.ProfileViewModel
import com.example.rollmat.register.RegisterScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    // Dependency injection -> library koji sluzi da bi se "ubrizgale ovisnosti"

    // state trenutnog screena

    private val state = mutableStateOf(
        MainScreenState.PROFILE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    JiuJitsuAppBars()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun JiuJitsuAppBars(
        viewModel: MainViewModel = koinViewModel(),
    ) {
        val currentScreen by remember { state }
        val isSignedIn by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.checkUserLoggedIn()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "ROLLMAT",
                            color = Color(0xFFE53935),
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            BadgedBox(
                                badge = {
                                    Badge(containerColor = Color(0xFFE53935))
                                }
                            ) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "Notifications"
                                )
                            }
                        }

                        IconButton(onClick = {}) {
                            Icon(
                                Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ) {
                    NavigationBarItem(
                        selected = currentScreen == MainScreenState.FEED,
                        onClick = { state.value = MainScreenState.FEED },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Feed") },
                        label = { Text("Feed") }
                    )

                    NavigationBarItem(
                        enabled = isSignedIn,
                        selected = currentScreen == MainScreenState.EXPLORE,
                        onClick = { state.value = MainScreenState.EXPLORE },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Explore"
                            )
                        },
                        label = { Text("Explore") }
                    )

                    NavigationBarItem(
                        enabled = isSignedIn,
                        selected = false,
                        onClick = { },
                        icon = {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE53935)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Post",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        },
                        label = { Text("Post") },
                    )
                    NavigationBarItem(
                        enabled = isSignedIn,
                        selected = currentScreen == MainScreenState.PROGRESS,
                        onClick = { state.value = MainScreenState.PROGRESS },
                        icon = { Icon(Icons.Default.EmojiEvents, contentDescription = "Progress") },
                        label = { Text("Progress") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == MainScreenState.PROFILE,
                        onClick = { state.value = MainScreenState.PROFILE },
                        icon = {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "Profile"
                            )
                        },
                        label = { Text("Profile") }
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                when (currentScreen) {
                    MainScreenState.FEED -> TODO()
                    MainScreenState.EXPLORE -> TODO()
                    MainScreenState.PROGRESS -> TODO()
                    MainScreenState.PROFILE -> {
                        ProfileScreen(
                            showLogin = { state.value = MainScreenState.LOGIN },
                            showRegister = {
                                state.value = MainScreenState.REGISTER
                            },
                            onSignOut = {
                                viewModel.logout()
                            }
                        )
                    }

                    MainScreenState.LOGIN -> {
                        LoginScreen(
                            onLoginSuccess = {
                                viewModel.checkUserLoggedIn()
                                state.value = MainScreenState.PROFILE
                            }
                        )
                    }

                    MainScreenState.REGISTER -> {
                        RegisterScreen(
                            onRegisterSuccess = {
                                state.value = MainScreenState.LOGIN
                            }
                        )
                    }
                }
            }
        }
    }
}