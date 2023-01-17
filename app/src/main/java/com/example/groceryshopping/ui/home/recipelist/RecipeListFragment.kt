package com.example.groceryshopping.ui.home.recipelist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryshopping.databinding.RecipeListFragmentBinding
import com.example.groceryshopping.sources.api.recipe.recipes.ExtendedRecipe
import com.example.groceryshopping.viewmodel.RecipeViewModel
import java.lang.RuntimeException

class RecipeListFragment : Fragment(), RecipeListRecyclerViewAdapter.OnClickListener {

    private var columnCount:Int = 1
    private lateinit var onClickListener: OnClickListener
    private lateinit var binding: RecipeListFragmentBinding
    private lateinit var adapter: RecipeListRecyclerViewAdapter
    private lateinit var viewmodel: RecipeViewModel

    interface OnClickListener {
        fun onClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnClickListener) {
            onClickListener = context
        }else{
            throw RuntimeException("Must implement onclicklistener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)

        val recyclerView = binding.recipeList
        recyclerView.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        adapter = RecipeListRecyclerViewAdapter(this)
        recyclerView.adapter = adapter

        // question to professor: how can know when the end of list is being viewed by the user
        // so that I can call another get request from the API to get more recipes, as currently if the
        // user does not choose any filters I can show up to 100 random recipes at a time so if they
        // reach the end how can I do another call on the recipes

        Log.d("recipe list", viewmodel.getRandomRecipes().toString())

//        if(viewmodel.getRandomRecipes() == emptyList<ExtendedRecipe>()) {
//            adapter.replaceItems(emptyList())
//        }else {

        if(viewmodel.getRandomRecipes() == emptyList<ExtendedRecipe>()) {
            adapter.replaceItems(emptyList())
        }else{
            viewmodel.getRandomRecipes().observe(viewLifecycleOwner, {
                adapter.replaceItems(it.recipes)
                viewmodel.initCurRecipe(adapter.getRecipe(0))
            })
        }

        binding.searchButton.setOnClickListener {
            if(binding.searchValue.text.toString() != "") {
//                viewmodel.getSearchRecipes(binding.searchValue.text.toString()).observe(viewLifecycleOwner, {
//                    adapter.replaceItems(it)
//                    viewmodel.initCurRecipe(adapter.getRecipe(0))
//                })
            }else{
                Toast.makeText(context, "To run a search please provide a query", Toast.LENGTH_SHORT).show()
            }
        }

        binding.refereshButton.setOnClickListener {
            viewmodel.initGetRecipes()
            viewmodel.getRandomRecipes().observe(viewLifecycleOwner, {
                adapter.replaceItems(it.recipes)
                viewmodel.initCurRecipe(adapter.getRecipe(0))
            })
        }

        viewmodel.curRecipe.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
        })
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            RecipeListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onItemClick(recipe: ExtendedRecipe) {
        viewmodel.setCurRecipe(recipe)
        onClickListener.onClick()
    }
}