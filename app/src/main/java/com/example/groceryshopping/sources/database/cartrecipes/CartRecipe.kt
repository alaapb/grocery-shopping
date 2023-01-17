package com.example.groceryshopping.sources.database.cartrecipes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartrecipes")

class CartRecipe (
    @PrimaryKey (autoGenerate = true) val id:Int,
    val name : String,
    val ingredients : List<String>,
    val quantity : String,
    val instructions : List<String>,
    val image : String,
    val imageType : String,
    val serving: Int
        )