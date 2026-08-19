package br.com.fiap.recipes.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import br.com.fiap.recipes.screens.CategoryRecipeScreen
import br.com.fiap.recipes.screens.HomeScreen
import br.com.fiap.recipes.screens.InitialScreen
import br.com.fiap.recipes.screens.LoginScreen
import br.com.fiap.recipes.screens.ProfileScreen
import br.com.fiap.recipes.screens.SignupScreen

@Composable
fun NavigationRoutes() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.InitialScreen.route
    ) {
        composable(route = Destination.InitialScreen.route) {
            InitialScreen(navController)
        }

        composable(
            route = Destination.HomeScreen.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://recipes.fiap.com.br/email/{email}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(navArgument("email") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            var email = backStackEntry.arguments?.getString("email")
            HomeScreen(navController, email)
        }

        composable(route = Destination.SignupScreen.route) {
            SignupScreen(navController)
        }

        composable(route = Destination.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(
            route = Destination.ProfileScreen.route,
            arguments = listOf(navArgument("email") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            var email = backStackEntry.arguments?.getString("email")
            ProfileScreen(navController, email)
        }

        composable(
            route = Destination.CategoryRecipeScreen.route,
            arguments = listOf(
                navArgument(name = "categoryId") {
                    type = NavType.IntType
                }
            )
        ) {
            val categoryId = it.arguments?.getInt("categoryId")
            CategoryRecipeScreen(categoryId, null)
        }
    }
}