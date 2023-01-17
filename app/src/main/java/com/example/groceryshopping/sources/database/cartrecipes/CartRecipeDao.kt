package com.example.groceryshopping.sources.database.cartrecipes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartRecipeDao {

    @Query ("SELECT * FROM cartrecipes")
    fun getAllCartRecipes() : LiveData<List<CartRecipe>>;

    @Insert
    fun addRecipeToCart(recipe:CartRecipe);

    @Delete
    fun delCartRecipe(recipe:CartRecipe);

    @Query("UPDATE cartrecipes SET quantity = :quantity WHERE id = :id")
    fun updateCartRecipe(quantity : String, id : Int);

    @Query("SELECT EXISTS(SELECT * FROM cartrecipes WHERE id = :id)")
    fun ifRowExists(id : Int) : Boolean

}