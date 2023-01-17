package com.example.groceryshopping.ui.home.cart

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryshopping.databinding.RecipeFragmentBinding
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipe
import java.util.concurrent.Executors

class CartListAdapter(
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    private var recipeList = mutableListOf<CartRecipe>()
    lateinit var curRecipe: CartRecipe

    interface OnClickListener {
        fun onItemClick(recipe: CartRecipe)
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
        holder.nameView.text = recipeList[position].name

        setImageView(holder, recipeList[position].image)

        holder.cardView.setOnClickListener {
            onClickListener.onItemClick(recipeList[position])
        }
    }

    fun setImageView(holder:ViewHolder, url : String) {

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        var image: Bitmap? = null

        executor.execute {
            try {
                val `in` = java.net.URL(url).openStream()
                image = BitmapFactory.decodeStream(`in`)

                // Only for making changes in UI
                handler.post {
                    holder.imageView.setImageBitmap(image)
                }
            }

            catch (e: Exception) {
                Log.d("cart adapter", "booo")
                e.printStackTrace()
            }
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

    fun replaceItems(recipes: List<CartRecipe>) {
        recipeList.clear()
        recipeList.addAll(recipes)
        notifyDataSetChanged()
    }

    fun getRecipe(position:Int) : CartRecipe {
//        if(recipeList.size > 0) {
        return recipeList[position]
//        }
//        return ExtendedRecipe()
    }

}