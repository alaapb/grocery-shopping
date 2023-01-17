package com.example.groceryshopping

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.example.groceryshopping.ui.home.CheckoutFragment
import com.example.groceryshopping.ui.home.cart.CartFragment
import com.example.groceryshopping.ui.home.MenuFragment
import com.example.groceryshopping.ui.home.recipelist.RecipeDetailFragment
import com.example.groceryshopping.ui.home.recipelist.RecipeListFragment
import com.example.groceryshopping.ui.home.cart.CartDetailFragment
import com.example.groceryshopping.viewmodel.CartViewModel
import com.example.groceryshopping.viewmodel.RecipeViewModel

class HomeActivity : AppCompatActivity(),
    RecipeListFragment.OnClickListener,
    MenuFragment.OnClickMenuListener,
    RecipeDetailFragment.OnClickDetailListener,
    CartFragment.OnClickCartListener,
    CartDetailFragment.OnCartDetailListener {

    private var recipeListContainer: FragmentContainerView? = null
    private var menuOptionsContainer: FragmentContainerView? = null
    lateinit var viewmodel: RecipeViewModel
    lateinit var cartViewModel: CartViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_fragment)

        recipeListContainer = findViewById<FragmentContainerView>(R.id.listContainerId)
        menuOptionsContainer = findViewById<FragmentContainerView>(R.id.menuOptionId)

        recipeListContainer?.let { frameLayout ->
            supportFragmentManager.beginTransaction()
                .add(frameLayout.id, RecipeListFragment.newInstance(1))
                .commit()
        }

        menuOptionsContainer?.let { frameLayout ->
            supportFragmentManager.beginTransaction()
                .add(frameLayout.id, MenuFragment.newInstance())
                .commit()
        }

        viewmodel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        viewmodel.initGetRecipes()

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
    }

    override fun goToHome() {
        recipeListContainer?.let { frameLayout ->
            supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, RecipeListFragment.newInstance(1))
                .commit()
        }
    }

    override fun goToCart() {
        recipeListContainer?.let { frameLayout ->
            supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, CartFragment.newInstance())
                .commit()
        }
    }

    override fun onClick() {
        recipeListContainer?.let { frameLayout ->
            supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, RecipeDetailFragment.newInstance())
                .commit()
        }
    }

    override fun onClickCartDetail() {
        recipeListContainer?.let { frameLayout ->
            supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, CartDetailFragment.newInstance())
                .commit()
        }
    }

    override fun backToCart() {
        recipeListContainer?.let { frameLayout ->
            supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, CartFragment.newInstance())
                .commit()
        }
    }

    override fun goToCheckout() {
        recipeListContainer?.let { frameLayout ->
            supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, CheckoutFragment.newInstance())
                .commit()
        }
    }

    override fun goToStores() {
        startActivity(Intent(this, MapsActivity::class.java))
    }

}