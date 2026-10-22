package com.grupo9.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grupo9.model.Sesion
import com.grupo9.ui.screen.AgendaScreen
import com.grupo9.ui.screen.BanqueteriaScreen
import com.grupo9.ui.screen.HomeScreen
import com.grupo9.ui.screen.LoginScreen
import com.grupo9.ui.screen.PanelAdminScreen
import com.grupo9.ui.screen.PerfilScreen
import com.grupo9.ui.screen.RegisterScreen
import com.grupo9.ui.screen.ReservaScreen
import com.grupo9.ui.viewmodel.AgendaViewmodel
import com.grupo9.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Routes {
    const val INICIO = "INICIO"
    const val LOGIN = "Login"
    const val REGISTER = "register"
    const val HOME = "Home"
    const val AGENDA = "Agenda"
    const val RESERVA = "Reserva"
    const val PERFIL = "Perfil"
    const val ADMIN = "Admin"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val demora = 500L // milisegundos de espera antes de cambiar de pantalla

    NavHost(
        navController = navController,
        startDestination = Routes.INICIO,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Routes.INICIO) {
            BanqueteriaScreen(
                onNavigateToLogin = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.LOGIN)
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.REGISTER)
                    }
                },
                onLoginSuccess = {
                    scope.launch {
                        delay(demora)
                        val destino = if (Sesion.esAdmin) Routes.ADMIN else Routes.HOME
                        navController.navigate(destino) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                },
                onNavigateToBack = {
                    scope.launch {
                        delay(demora)
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Routes.HOME) {
            val homeViewModel: HomeViewModel = viewModel()

            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToReserva = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.RESERVA) { launchSingleTop = true }
                    }
                },
                onNavigateToAgenda = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.AGENDA) { launchSingleTop = true }
                    }
                },
                onNavigateToPerfil = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.PERFIL) { launchSingleTop = true }
                    }
                }
            )
        }

        composable(Routes.AGENDA) {
            val context = LocalContext.current
            val agendaViewmodel: AgendaViewmodel = viewModel()

            LaunchedEffect(Unit) {
                agendaViewmodel.cargarReserva(context)
            }

            AgendaScreen(
                onNavigateToHome = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                    }
                },
                onNavigateToReserva = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.RESERVA) { launchSingleTop = true }
                    }
                },
                onNavigateToPerfil = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.PERFIL) { launchSingleTop = true }
                    }
                },
                agendaViewmodel = agendaViewmodel
            )
        }

        composable(Routes.RESERVA) {
            ReservaScreen(
                onNavigateToHome = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                    }
                },
                onNavigateToAgenda = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.AGENDA) { launchSingleTop = true }
                    }
                },
                onNavigateToPerfil = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.PERFIL) { launchSingleTop = true }
                    }
                }
            )
        }

        composable(Routes.PERFIL) {
            PerfilScreen(
                onNavigateToHome = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                    }
                },
                onNavigateToAgenda = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.AGENDA) { launchSingleTop = true }
                    }
                },
                onNavigateToReserva = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.RESERVA) { launchSingleTop = true }
                    }
                }
            )
        }

        composable(Routes.ADMIN) {
            PanelAdminScreen(
                onLogout = {
                    scope.launch {
                        delay(demora)
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.ADMIN) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}