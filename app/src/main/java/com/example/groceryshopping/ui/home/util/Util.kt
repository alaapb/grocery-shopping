package com.example.groceryshopping.ui.home.util


import android.widget.ImageView
import com.example.groceryshopping.sources.api.recipe.recipes.AnalyzedInstruction
import com.example.groceryshopping.sources.api.recipe.recipes.ExtendedIngredient
import com.squareup.picasso.Picasso

class Util : UtilInterface {

    override fun getIngredients(ingredients : List<ExtendedIngredient>) : List<String> {
        val listOfIngredients : ArrayList<String> = ArrayList()
        for (ingredient in ingredients) {
            listOfIngredients.add(ingredient.name + "; " + String.format("%.3f", ingredient.measures.us.amount) +
                    "; " + ingredient.measures.us.unitShort + "\n")
        }
        return listOfIngredients
    }

    override fun getCartIngredients(ingredients: List<ExtendedIngredient>, quantity : Double, servingSize : Int) : List<String> {
        val listOfIngredients : ArrayList<String> = ArrayList()
        for(ingredient in ingredients) {
            var temp = 0.0

            if(servingSize != 1) {
                temp = ingredient.measures.us.amount / servingSize
            }

            temp*=quantity

            val stringTemp = String.format("%.3f", temp)

            listOfIngredients.add(ingredient.name + "; " + stringTemp + "; " + ingredient.measures.us.unitShort + "\n")
        }

        return listOfIngredients
    }

    override fun replaceCommaSemiBrackets(string : String) : String {
        return string
            .replace(",", "")
            .replace(";", "")
            .replace("[","")
            .replace("]","")
            .trim()
    }

    override fun setImageView(imageView: ImageView, url : String?) {
        if(!url.equals(null)) {
            Picasso.get().load(url).into(imageView)
        }
    }

    override fun parseInstruction(analyzedInstruction: List<AnalyzedInstruction>) : List<String> {
        val result : ArrayList<String> = ArrayList()
        for(instr in analyzedInstruction) {
            for (step in instr.steps) {
                result.add(step.number.toString() + ". ; " + step.step + "\n")
            }
        }
        return result
    }
}