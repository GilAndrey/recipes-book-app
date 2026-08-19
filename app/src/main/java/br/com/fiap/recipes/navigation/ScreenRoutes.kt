package br.com.fiap.recipes.navigation

sealed class Destination(val route: String) {

    object InitialScreen: Destination("initial")

    object SignupScreen: Destination("signup")

    object ProfileScreen: Destination("profile/{email}") {
        fun createRoute(email: String): String {
            return "profile/$email"
        }
    }

    object HomeScreen: Destination("home/{email}") {
        fun createRoute(email: String): String {
            return "home/$email"
        }
    }

    object LoginScreen: Destination("login")

    object CategoryRecipeScreen: Destination("category/{categoryId}") {
        fun createRoute(categoryId: Int): String {
            return "category/$categoryId"
        }
    }

}