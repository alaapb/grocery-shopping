package com.example.groceryshopping

import android.app.Application
import androidx.room.Room
import com.example.groceryshopping.repository.cartrepo.CartRecipeImp
import com.example.groceryshopping.sources.database.DatabaseImplementation
//import com.example.groceryshopping.repository.cartrepo.CartRecipeImp
import com.example.groceryshopping.repository.cartrepo.CartRecipeRepository
import com.example.groceryshopping.repository.placesrepo.PlacesRepository
import com.example.groceryshopping.repository.placesrepo.PlacesRepositoryImp
import com.example.groceryshopping.repository.reciperepo.RecipeRepository
import com.example.groceryshopping.repository.reciperepo.RecipeRepositoryImp
import com.example.groceryshopping.sources.api.nearby.NearbyAPIService
import com.example.groceryshopping.sources.api.recipe.RecipeAPIService
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class RecipeApplication : Application() {
    lateinit var recipeRepository: RecipeRepository
    lateinit var cartRepository: CartRecipeRepository
    lateinit var placesRepository: PlacesRepository
    private lateinit var database: DatabaseImplementation

    override fun onCreate() {
        super.onCreate()

        val retrofitNearby = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/recipes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val recipesAPI = retrofit.create(RecipeAPIService::class.java)

        val placesAPI = retrofitNearby.create(NearbyAPIService::class.java)

        database =
            Room.databaseBuilder(
                applicationContext, DatabaseImplementation::class.java,
                "grocery-shopping-db"
            ).build()

        recipeRepository = RecipeRepositoryImp(recipesAPI)

        placesRepository = PlacesRepositoryImp(placesAPI)

        cartRepository = CartRecipeImp(
            Executors.newSingleThreadExecutor(),
            database.cartRecipeDao())
    }

}