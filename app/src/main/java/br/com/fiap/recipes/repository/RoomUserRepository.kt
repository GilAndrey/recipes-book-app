package br.com.fiap.recipes.repository

import android.content.Context
import br.com.fiap.recipes.dao.RecipeDatabase
import br.com.fiap.recipes.model.User

class RoomUserRepository(context: Context): UserRepository {

    private val recipeDatabase = RecipeDatabase.getDatabase(context).userDao()

    override fun saveUser(user: User) {
        recipeDatabase.save(user)
    }

    override fun getUser(): User {
        TODO("Not yet implemented")
    }

    override fun getUser(id: Int): User {
        return recipeDatabase.getUserById(id) ?: User()
    }

    override fun getUserByEmail(email: String): User? {
        return recipeDatabase.getUserByEmail(email)
    }

    override fun login(email: String, password: String): Boolean {
        val user = recipeDatabase.login(email, password)
        return user != null
    }

    override fun updateUser(user: User): Int {
        return recipeDatabase.update(user)
    }

    override fun delete(user: User): Int {
        return recipeDatabase.delete(user)
    }


}