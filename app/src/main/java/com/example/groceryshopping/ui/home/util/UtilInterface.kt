package com.example.groceryshopping.ui.home.util

import android.widget.ImageView
import com.example.groceryshopping.sources.api.recipe.recipes.AnalyzedInstruction
import com.example.groceryshopping.sources.api.recipe.recipes.ExtendedIngredient

interface UtilInterface {

    fun getIngredients(ingredients : List<ExtendedIngredient>) : List<String>
    fun getCartIngredients(ingredients: List<ExtendedIngredient>, quantity : Double, servingSize : Int) : List<String>

    fun parseInstruction(analyzedInstruction: List<AnalyzedInstruction>) : List<String>

    fun replaceCommaSemiBrackets(string : String) : String
//    fun substringOnce(string : String) : String
//    fun substringTwice(string : String) : String

    fun setImageView(imageView: ImageView, url: String?)

}