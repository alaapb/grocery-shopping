package com.example.groceryshopping.repository.cartrepo

import androidx.lifecycle.LiveData
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipe


interface CartRecipeRepository {

    fun addCartRecipe(recipe: CartRecipe);
    fun getAllCartRecipe(): LiveData<List<CartRecipe>>
    fun delCartRecipe(recipe: CartRecipe);
    fun updateCartRecipe(quantity : String, id : Int)

    fun ifRowExists(id : Int) : Boolean
}