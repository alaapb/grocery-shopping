package com.example.groceryshopping.repository.cartrepo

import androidx.lifecycle.LiveData
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipe
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipeDao
//import com.example.groceryshopping.sources.database.cartrecipes.CartRecipeDao
import java.util.concurrent.Executor

class CartRecipeImp(
    private val executor: Executor,
    private val cartRecipeDao: CartRecipeDao
) : CartRecipeRepository {

    override fun addCartRecipe(recipe: CartRecipe) {
        executor.execute {
            cartRecipeDao.addRecipeToCart(recipe)
        }
    }

    override fun getAllCartRecipe(): LiveData<List<CartRecipe>> {
        return cartRecipeDao.getAllCartRecipes()
    }

    override fun delCartRecipe(recipe: CartRecipe) {
        executor.execute {
            cartRecipeDao.delCartRecipe(recipe)
        }
    }

    override fun updateCartRecipe(quantity: String, id: Int) {
        executor.execute {
            cartRecipeDao.updateCartRecipe(quantity, id)
        }
    }

    override fun ifRowExists(id: Int): Boolean {
        var temp : Boolean = false
        executor.execute {
            temp = cartRecipeDao.ifRowExists(id)
        }
        return temp
    }
}