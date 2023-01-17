package com.example.groceryshopping.repository.reciperepo

import androidx.lifecycle.LiveData
import com.example.groceryshopping.sources.api.recipe.recipes.ExtendedRecipe
import com.example.groceryshopping.sources.api.recipe.recipes.Recipes

interface RecipeRepository {

//    fun addRecipe(recipe: Recipe);
//    fun getAllRecipes() : LiveData<List<Recipe>>;

    fun getIdBulkRecipes(id : String) : LiveData<List<ExtendedRecipe>>
    fun getRandomRecipes() : LiveData<Recipes>


//    fun getSearchRecipes(query : String) : LiveData<ComplexSearch>
    fun getIdRecipe(id : String) : LiveData<ExtendedRecipe>
}