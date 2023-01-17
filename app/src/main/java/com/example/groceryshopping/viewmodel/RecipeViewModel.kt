package com.example.groceryshopping.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groceryshopping.RecipeApplication
import com.example.groceryshopping.repository.reciperepo.RecipeRepository
import com.example.groceryshopping.sources.api.recipe.recipes.ExtendedRecipe
import com.example.groceryshopping.sources.api.recipe.recipes.Recipes

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val recipeRepository: RecipeRepository =
        (application as RecipeApplication).recipeRepository

    var recipes : LiveData<Recipes> = MutableLiveData()
    var curRecipe: MutableLiveData<ExtendedRecipe> = MutableLiveData()

    fun initCurRecipe(recipe: ExtendedRecipe) {
        if(curRecipe.value == null) {
            curRecipe.value = recipe
        }
    }

    fun initGetRecipes() {
        recipes = recipeRepository.getRandomRecipes()
    }

    fun setCurRecipe(recipe: ExtendedRecipe) {
        curRecipe.value = recipe
    }

//    fun addRecipe(recipe: Recipe) {
//        recipeRepository.addRecipe(recipe)
//    }

//    fun getAllRecipes() : LiveData<List<Recipe>> {
//        return recipeRepository.getAllRecipes()
//    }


//    fun getSearchRecipes(query : String) : LiveData<List<ExtendedRecipe>> {
//        var searchRecipes : LiveData<ComplexSearch> = recipeRepository.getSearchRecipes(query)
//
//        var ids : String = ""
//
//        for(recipe in searchRecipes.value!!.results) {
//            ids = recipe.id.toString() + ","
//            Log.d("recipe view model", recipe.id.toString())
//        }
//
//        ids.dropLast(1)
//
//        return recipeRepository.getIdBulkRecipes(ids)
//    }



    fun getRandomRecipes() : LiveData<Recipes> {
        return recipes
    }
}