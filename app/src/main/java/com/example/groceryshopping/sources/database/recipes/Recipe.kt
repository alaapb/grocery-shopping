package com.example.groceryshopping.sources.database.recipes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey (autoGenerate = true) val id: Int,
    var name: String,
    var ingredients: List<String>) {
//    companion object {
//        var curRecipeId:Int = 0
//        var com.example.groceryshopping.sources.database.recipes:ArrayList<Recipe> = arrayListOf<Recipe>(
//            Recipe(0, "Ravioli", listOf("pasta, ricotta cheese, spinach")),
//            Recipe(1, "Ramen", listOf("ramen noodles, chasu pork, broth, onions")),
//            Recipe(2, "Chicken Wings", listOf("chicken, hot sauce, ranch, salt, pepper")),
//            Recipe(3, "Tiramisu", listOf("coffee, vanilla cream, eggs, flour, biscuits"))
//        )
//    }
}