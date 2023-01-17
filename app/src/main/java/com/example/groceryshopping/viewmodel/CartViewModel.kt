package com.example.groceryshopping.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groceryshopping.RecipeApplication
import com.example.groceryshopping.repository.cartrepo.CartRecipeRepository
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipe

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartRepository: CartRecipeRepository =
        (application as RecipeApplication).cartRepository


    var curRecipe : MutableLiveData<CartRecipe> = MutableLiveData()

    fun initCurRecipe(recipe:CartRecipe) {
        if(curRecipe.value == null) {
            curRecipe.value = recipe
        }
    }

    fun setCurRecipe(recipe:CartRecipe) {
        curRecipe.value = recipe
    }

    fun addCartRecipe(recipe:CartRecipe) {
        cartRepository.addCartRecipe(recipe)
    }

    fun getAllCartRecipes() : LiveData<List<CartRecipe>> {
        return cartRepository.getAllCartRecipe()
    }

    fun delCartRecipe(recipe:CartRecipe) {
        cartRepository.delCartRecipe(recipe)
    }

    fun updateCartRecipe(quantity : String, id : Int) {
        cartRepository.updateCartRecipe(quantity, id)
    }

    fun ifRowExists(id : Int) : Boolean {
        return cartRepository.ifRowExists(id)
    }

}