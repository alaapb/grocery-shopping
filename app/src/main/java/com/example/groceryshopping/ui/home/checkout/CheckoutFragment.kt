package com.example.groceryshopping.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.groceryshopping.databinding.CheckoutFragmentBinding
import com.example.groceryshopping.ui.home.util.Util
import com.example.groceryshopping.ui.home.util.UtilInterface
import com.example.groceryshopping.viewmodel.CartViewModel

class CheckoutFragment : Fragment() {

    private lateinit var binding : CheckoutFragmentBinding
    private var util : UtilInterface = Util()

    companion object {
        fun newInstance() = CheckoutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CheckoutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewmodel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        val usedIngredients : ArrayList<CheckoutIngredients> = ArrayList()
        val ingNames : ArrayList<String> = ArrayList()

        viewmodel.getAllCartRecipes().observe(viewLifecycleOwner, {
            for(recipe in it!!) {
                for(ing in recipe.ingredients) {
                    var temp : List<String> = ing.splitToSequence(";").toList()
                    var ingredient = CheckoutIngredients(util.replaceCommaSemiBrackets(temp[0]), temp[1].toDouble(), util.replaceCommaSemiBrackets(temp[2]))
                    if(ingredient.ifExists(ingredient.name, usedIngredients)) {
                        val oldIngredient = ingredient.getIngredient(ingredient.name, usedIngredients)
                        usedIngredients.remove(oldIngredient)
                        if(ingredient.unit == oldIngredient.unit) {
                            usedIngredients.add(CheckoutIngredients(ingredient.name, oldIngredient.amount + ingredient.amount, ingredient.unit))
                        }else{
                            val newVal = checkUnit(ingredient.unit, oldIngredient.unit, oldIngredient.amount, ingredient.amount)
                            usedIngredients.add(CheckoutIngredients(ingredient.name, newVal, oldIngredient.unit))
                        }
                    }else {
                        usedIngredients.add(ingredient)
                    }
                }
            }
            for(ingredient in usedIngredients) {
                ingNames.add(ingredient.name + " " + ingredient.amount + ingredient.unit)
            }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, ingNames)
            binding.checkoutListView.adapter = adapter
            // all used ingredients have been added, currently there be duplicates but will have to change that
        })


    }

    fun checkUnit(currUnit : String, oldUnit: String, oldVal : Double, currVal : Double) : Double {
        // cup to onces
        // onces to ml
        // ml to tbsp

        var newVal : Double = 0.0

        if(ifCup(currUnit)) {
            if(ifOz(oldUnit)) {
                newVal = oldVal + cupToOz(currVal)
            }else if(ifTbsp(oldUnit)) {
                newVal = currVal + tbspToCup(oldVal)
            }else if(ifTsp(oldUnit)) {
                newVal = currVal + tspToCup(oldVal)
            }
        }else if(ifOz(currUnit)) {
            if(ifCup(oldUnit)) {
                newVal = currVal + cupToOz(oldVal)
            }else if(ifTbsp(oldUnit)) {
                newVal = currVal + cupToOz(tbspToCup(oldVal))
            }else if(ifTsp(oldUnit)) {
                newVal = currVal + cupToOz(tspToCup(oldVal))
            }
        }else if(ifTbsp(currUnit)) {
            if(ifOz(oldUnit)) {
                newVal = oldVal + cupToOz(tbspToCup(currVal))
            }else if(ifCup(oldUnit)) {
                newVal = oldVal + tbspToCup(currVal)
            }else if(ifTsp(oldUnit)) {
                newVal = currVal + tspToTbsp(oldVal)
            }
        }else if(ifTsp(currUnit)) {
            if(ifOz(oldUnit)) {
                newVal = oldVal + cupToOz(tspToCup(currVal))
            }else if(ifCup(oldUnit)) {
                newVal = oldVal + tspToCup(currVal)
            }else if(ifTbsp(oldUnit)) {
                newVal = oldVal + tspToTbsp(currVal)
            }
        }

        return String.format(".3f", newVal).toDouble()

    }

    fun tspToTbsp(value : Double) : Double {
        return value / 3
    }

    fun tspToCup(value : Double) : Double {
        return value / 48
    }

    fun tbspToCup(value : Double) : Double {
        return value / 16
    }

    fun cupToOz(value : Double) : Double {
        return value * 8
    }

    fun ifCup(string : String) : Boolean {
        if(string == "cups" || string == "cup") {
            return true
        }
        return false
    }

    fun ifTsp(string : String) : Boolean {
        if(string == "tsp" || string == "tsps") {
            return true
        }
        return false
    }

    fun ifTbsp(string : String) : Boolean {
        if(string == "tbsp" || string == "Tbsp" || string == "tbsps" || string == "Tbsps") {
            return true
        }
        return false
    }

    fun ifOz(string : String) : Boolean {
        if(string == "oz" || string == "Oz") {
            return true
        }
        return false
    }

    fun ifServings(string : String) : Boolean {
        if(string == "serving" || string == "servings" || string == "Servings" || string == "Serving") {
            return true
        }
        return false
    }

}


data class CheckoutIngredients(val name : String, val amount : Double, val unit : String) {

    fun ifExists(name : String, ingredients: List<CheckoutIngredients>) : Boolean {
        for(ingredient in ingredients) {
            if(ingredient.name == name) {
                return true
            }
        }
        return false
    }

    fun getIngredient(name : String, ingredients: List<CheckoutIngredients>) : CheckoutIngredients{
        for(ingredient in ingredients) {
            if(ingredient.name == name) {
                return ingredient
            }
        }
        return CheckoutIngredients("", 0.0, "")
    }
}