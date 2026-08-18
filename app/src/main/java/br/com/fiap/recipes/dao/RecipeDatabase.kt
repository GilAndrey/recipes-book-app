package br.com.fiap.recipes.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.recipes.model.User

@Database(entities = [User::class], version = 1)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private lateinit var instance: RecipeDatabase

        fun getDatabase(context: Context): RecipeDatabase {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        RecipeDatabase::class.java,
                        "recipe_database"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }

}