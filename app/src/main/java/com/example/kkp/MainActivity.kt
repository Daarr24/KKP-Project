package com.example.kkp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kkp.api.SessionManager
import com.example.kkp.ui.screens.AssetScreen
import com.example.kkp.ui.screens.DashboardScreen
import com.example.kkp.ui.screens.LoginScreen
import com.example.kkp.ui.screens.ProfileScreen
import com.example.kkp.ui.screens.RentalScreen
import com.example.kkp.ui.theme.KKPTheme
import com.example.kkp.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize SessionManager
        SessionManager.init(this)
        
        setContent {
            KKPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KKPApp()
                }
            }
        }
    }
}

@Composable
fun KKPApp() {
    val authViewModel: AuthViewModel = viewModel()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = if (isLoggedIn) "dashboard" else "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {},
                authViewModel = authViewModel,
                navController = navController
            )
        }
        
        composable("dashboard") {
            DashboardScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToAssets = {
                    navController.navigate("assets")
                },
                onNavigateToProjects = {
                    navController.navigate("projects")
                },
                onNavigateToRental = {
                    navController.navigate("rental")
                },
                onNavigateToPengiriman = {
                    navController.navigate("pengiriman")
                },
                onNavigateToTagihan = {
                    navController.navigate("tagihan")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                },
                authViewModel = authViewModel
            )
        }
        
        composable("assets") {
            AssetScreen()
        }
        
        composable("projects") {
            // TODO: Implement ProjectScreen
            DashboardScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToAssets = {
                    navController.navigate("assets")
                },
                onNavigateToProjects = {
                    navController.navigate("projects")
                },
                onNavigateToRental = {
                    navController.navigate("rental")
                },
                onNavigateToPengiriman = {
                    navController.navigate("pengiriman")
                },
                onNavigateToTagihan = {
                    navController.navigate("tagihan")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                },
                authViewModel = authViewModel
            )
        }
        
        composable("rental") {
            RentalScreen(
                onNavigateToProfile = {
                    navController.navigate("profile")
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }
        
        composable("pengiriman") {
            // TODO: Implement PengirimanScreen
            DashboardScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToAssets = {
                    navController.navigate("assets")
                },
                onNavigateToProjects = {
                    navController.navigate("projects")
                },
                onNavigateToRental = {
                    navController.navigate("rental")
                },
                onNavigateToPengiriman = {
                    navController.navigate("pengiriman")
                },
                onNavigateToTagihan = {
                    navController.navigate("tagihan")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                },
                authViewModel = authViewModel
            )
        }
        
        composable("tagihan") {
            // TODO: Implement TagihanScreen
            DashboardScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToAssets = {
                    navController.navigate("assets")
                },
                onNavigateToProjects = {
                    navController.navigate("projects")
                },
                onNavigateToRental = {
                    navController.navigate("rental")
                },
                onNavigateToPengiriman = {
                    navController.navigate("pengiriman")
                },
                onNavigateToTagihan = {
                    navController.navigate("tagihan")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                },
                authViewModel = authViewModel
            )
        }
        
        composable("profile") {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }
    }
} 