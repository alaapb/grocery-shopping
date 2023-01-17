package com.example.groceryshopping.repository.reciperepo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groceryshopping.sources.api.recipe.RecipeAPIService
import com.example.groceryshopping.sources.api.recipe.recipes.ExtendedRecipe
import com.example.groceryshopping.sources.api.recipe.recipes.Recipes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecipeRepositoryImp(
    private val recipeAPIService: RecipeAPIService,
) : RecipeRepository {

    val numberOfRecipes = "100"
    val API_KEY = "dd427b2ce6ff4feb88b718cf6838dbce"


//    override fun getSearchRecipes(query : String) : LiveData<ComplexSearch> {
//        val result = MutableLiveData<ComplexSearch>()
//
//        recipeAPIService.getSearchRecipes(API_KEY, query).enqueue(object :
//        Callback<ComplexSearch> {
//
//            override fun onFailure(call: Call<ComplexSearch>, t: Throwable) {
//                Log.d("recipe repo", "did we reach")
//                result.postValue(null)
//            }
//
//            override fun onResponse(call: Call<ComplexSearch>, response: Response<ComplexSearch>) {
//                if(response.isSuccessful) {
//                    result.postValue(response.body())
//                }else{
//                    Log.d("recipe repo", "nope we dont")
//                    result.postValue(null)
//                }
//            }
//        })
//
//        return result
//    }

    override fun getIdBulkRecipes(id: String): LiveData<List<ExtendedRecipe>> {
        val result = MutableLiveData<List<ExtendedRecipe>>()

        recipeAPIService.getIdBulkRecipes(API_KEY, id).enqueue(object :
        Callback<List<ExtendedRecipe>> {
            override fun onFailure(call: Call<List<ExtendedRecipe>>, t: Throwable) {
                result.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ExtendedRecipe>>,
                response: Response<List<ExtendedRecipe>>
            ) {
                if(response.isSuccessful) {
                    result.postValue(response.body())
                }else{
                    result.postValue(null)
                }
            }
        })

        return result
    }



    override fun getIdRecipe(id : String) : LiveData<ExtendedRecipe> {
        val result = MutableLiveData<ExtendedRecipe>()

        recipeAPIService.getIdRecipe(API_KEY, id).enqueue(object :
            Callback<ExtendedRecipe> {
            override fun onFailure(call: Call<ExtendedRecipe>, t: Throwable) {
                Log.d("id recipe", "failure for get recipe id")
                result.postValue(null)
            }

            override fun onResponse(call: Call<ExtendedRecipe>, response: Response<ExtendedRecipe>) {
                if(response.isSuccessful) {
                    result.postValue(response.body())
                }else{
                    result.postValue(null)
                }
            }
        })

        return result
    }

    override fun getRandomRecipes(): LiveData<Recipes> {
        val result = MutableLiveData<Recipes>()

        recipeAPIService.getRandomRecipes(API_KEY, numberOfRecipes).enqueue(object :
            Callback<Recipes> {

            override fun onFailure(call: Call<Recipes>, t: Throwable) {
                Log.d("recipe repo", "failure")
                result.postValue(null)
            }

            override fun onResponse(call: Call<Recipes>, response: Response<Recipes>) {
                if(response.isSuccessful) {
                    result.postValue(response.body())
                }else{
                    //Log.d("recipe repo", "failure2")

                    Log.d("recipe repo", response.errorBody().toString())
                    result.postValue(null)
                }
            }
        })

        return result
    }

}