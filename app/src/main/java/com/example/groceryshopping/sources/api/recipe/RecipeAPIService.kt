package com.example.groceryshopping.sources.api.recipe

import com.example.groceryshopping.sources.api.recipe.recipes.ExtendedRecipe
import com.example.groceryshopping.sources.api.recipe.recipes.Recipes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeAPIService {

    @GET("random")
    fun getRandomRecipes(@Query("apiKey") api_key : String,
                        @Query("number") number : String) : Call<Recipes>

//    @GET("complexSearch")
//    fun getSearchRecipes(@Query("apiKey") api_key : String,
//                        @Query("query") query : String) : Call<ComplexSearch>

    @GET("informationBulk")
    fun getIdBulkRecipes(@Query("apiKey") api_key: String,
                        @Query("ids") id : String) : Call<List<ExtendedRecipe>>


    @GET("{id}/information")
    fun getIdRecipe(@Path("id") id : String,
                    @Query("apiKey") api_key: String) : Call<ExtendedRecipe>
}