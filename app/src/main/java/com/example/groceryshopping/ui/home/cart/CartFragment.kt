package com.example.groceryshopping.ui.home.cart

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryshopping.databinding.CartFragmentBinding
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipe
import com.example.groceryshopping.viewmodel.CartViewModel
import java.lang.RuntimeException
import java.util.concurrent.Executors

class CartFragment : Fragment(), CartListAdapter.OnClickListener {
    // shows all the com.example.groceryshopping.sources.database.recipes that have been chosen by the user
    private var columnCount:Int = 1
    private lateinit var onClickCartListener: OnClickCartListener
    private lateinit var binding : CartFragmentBinding
    private lateinit var viewmodel : CartViewModel
    private lateinit var adapter: CartListAdapter

    interface OnClickCartListener {
        fun goToHome();
        fun onClickCartDetail();
    }

    companion object {
        fun newInstance() = CartFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnClickCartListener) {
            onClickCartListener = context
        }else{
            throw RuntimeException("Check CallBack for error")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        val recyclerview = binding.cartList
        recyclerview.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        adapter = CartListAdapter(this)
        recyclerview.adapter = adapter

        viewmodel.getAllCartRecipes().observe(viewLifecycleOwner, {
            if(it.isEmpty()) {
                adapter.replaceItems(emptyList())
                Toast.makeText(context, "Please Add to the Cart to View", Toast.LENGTH_SHORT).show()
            }else {
                adapter.replaceItems(it)
                viewmodel.initCurRecipe(adapter.getRecipe(0))
            }
        })

        viewmodel.curRecipe.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
        })
    }



    override fun onItemClick(recipe: CartRecipe) {
        viewmodel.setCurRecipe(recipe)
        onClickCartListener.onClickCartDetail()
    }

}