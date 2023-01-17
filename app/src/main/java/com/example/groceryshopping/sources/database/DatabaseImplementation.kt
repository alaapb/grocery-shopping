package com.example.groceryshopping.sources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipe
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipeDao
//import com.example.groceryshopping.sources.database.cartrecipes.CartRecipe
//import com.example.groceryshopping.sources.database.cartrecipes.CartRecipeDao

@Database(
    entities = [CartRecipe::class],
    version  = 1
)
@TypeConverters(DataTypeConverter::class)
abstract class DatabaseImplementation : RoomDatabase() {
//    abstract fun recipeDao(): RecipeDao
    abstract fun cartRecipeDao(): CartRecipeDao
}