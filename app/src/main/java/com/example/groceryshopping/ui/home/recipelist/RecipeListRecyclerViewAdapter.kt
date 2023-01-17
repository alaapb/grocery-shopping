package com.example.groceryshopping.ui.home.recipelist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryshopping.databinding.RecipeFragmentBinding
import com.example.groceryshopping.sources.api.recipe.recipes.ExtendedRecipe
import com.example.groceryshopping.ui.home.util.Util
import com.example.groceryshopping.ui.home.util.UtilInterface

class RecipeListRecyclerViewAdapter(
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<RecipeListRecyclerViewAdapter.ViewHolder>() {

    private var recipeList = mutableListOf<ExtendedRecipe>()
    lateinit var curRecipe: ExtendedRecipe
    private var util : UtilInterface = Util()

    interface OnClickListener {
        fun onItemClick(recipe: ExtendedRecipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecipeFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        curRecipe = recipeList[position]
        holder.nameView.text = recipeList[position].title
        util.setImageView(holder.imageView, recipeList[position].image)

        holder.cardView.setOnClickListener {
            onClickListener.onItemClick(recipeList[position])
        }
    }

    override fun getItemCount(): Int = recipeList.size

    inner class ViewHolder(binding: RecipeFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.recipeName
        val cardView: CardView = binding.recipeCard
        val imageView: ImageView = binding.imageView


        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }



    fun replaceItems(recipes: List<ExtendedRecipe>) {
        recipeList.clear()
        recipeList.addAll(recipes)
        notifyDataSetChanged()
    }

    fun getRecipe(position:Int) : ExtendedRecipe {
//        if(recipeList.size > 0) {
            return recipeList[position]
//        }
//        return ExtendedRecipe()
    }
}