package com.grupo9.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grupo9.ui.screen.AgendaScreen
import com.grupo9.ui.screen.BanqueteriaScreen
import com.grupo9.ui.screen.HomeScreen
import com.grupo9.ui.screen.LoginScreen
import com.grupo9.ui.screen.PerfilScreen
import com.grupo9.ui.screen.RegisterScreen
import com.grupo9.ui.screen.ReservaScreen
import com.grupo9.ui.viewmodel.AgendaViewmodel
import com.grupo9.ui.viewmodel.HomeViewModel

object Routes {
    const val INICIO = "INICIO"
    const val LOGIN = "Login"
    const val REGISTER = "register"
    const val HOME = "Home"
    const val AGENDA = "Agenda"
    const val RESERVA = "Reserva"
    const val PERFIL = "Perfil"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.INICIO,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Routes.INICIO) {
            BanqueteriaScreen(
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HOME) {
            val homeViewModel: HomeViewModel = viewModel()

            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToReserva = {
                    navController.navigate(Routes.RESERVA) { launchSingleTop = true }
                },
                onNavigateToAgenda = {
                    navController.navigate(Routes.AGENDA) { launchSingleTop = true }
                },
                onNavigateToPerfil = {
                    navController.navigate(Routes.PERFIL) { launchSingleTop = true }
                }
            )
        }

        composable(Routes.AGENDA) {
            val context = LocalContext.current
            val agendaViewmodel: AgendaViewmodel = viewModel()

            agendaViewmodel.cargarReserva(context)

            AgendaScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onNavigateToReserva = {
                    navController.navigate(Routes.RESERVA) { launchSingleTop = true }
                },
                onNavigateToPerfil = {
                    navController.navigate(Routes.PERFIL) { launchSingleTop = true }
                },
                agendaViewmodel = agendaViewmodel
            )
        }

        composable(Routes.RESERVA) {
            ReservaScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onNavigateToAgenda = {
                    navController.navigate(Routes.AGENDA) { launchSingleTop = true }
                },
                onNavigateToPerfil = {
                    navController.navigate(Routes.PERFIL) { launchSingleTop = true }
                }
            )
        }

        composable(Routes.PERFIL) {
            PerfilScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onNavigateToAgenda = {
                    navController.navigate(Routes.AGENDA) { launchSingleTop = true }
                },
                onNavigateToReserva = {
                    navController.navigate(Routes.RESERVA) { launchSingleTop = true }
                }
            )
        }
    }
}