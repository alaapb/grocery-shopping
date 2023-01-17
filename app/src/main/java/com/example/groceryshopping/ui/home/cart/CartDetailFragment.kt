package com.example.groceryshopping.ui.home.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.groceryshopping.databinding.CartDetailFragmentBinding
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipe
import com.example.groceryshopping.ui.home.util.Util
import com.example.groceryshopping.ui.home.util.UtilInterface
import com.example.groceryshopping.viewmodel.CartViewModel
import java.lang.RuntimeException

class CartDetailFragment : Fragment() {

    private lateinit var binding : CartDetailFragmentBinding
    private lateinit var onClickListener:OnCartDetailListener
    private var util : UtilInterface = Util()

    interface OnCartDetailListener {
        fun backToCart()
    }

    companion object {
        fun newInstance() = CartDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CartDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnCartDetailListener) {
            onClickListener = context
        }else{
            throw RuntimeException("implement oncartdetaillistener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewmodel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        viewmodel.curRecipe.observe(viewLifecycleOwner, {
            binding.recipeTitle.text = it.name
            binding.recipeIngredients.text = util.replaceCommaSemiBrackets(it.ingredients.toString())
            binding.instructions.text = util.replaceCommaSemiBrackets(it.instructions.toString())
            binding.servingVal.text = it.quantity.toString()
            Log.d("cart detail", it.image)
            util.setImageView(binding.recipeImage, it.image)
        })

        Log.d("cart detail", viewmodel.curRecipe.value!!.id.toString())


        Log.d("cart detail", viewmodel.curRecipe.value!!.ingredients.toString())

        binding.deleteRecipeButton.setOnClickListener {
            viewmodel.delCartRecipe(
                CartRecipe(
                    viewmodel.curRecipe.value!!.id,
                    viewmodel.curRecipe.value!!.name,
                    viewmodel.curRecipe.value!!.ingredients,
                    viewmodel.curRecipe.value!!.quantity,
                    viewmodel.curRecipe.value!!.instructions,
                    viewmodel.curRecipe.value!!.image,
                    viewmodel.curRecipe.value!!.imageType,
                    viewmodel.curRecipe.value!!.serving)
            )
            onClickListener.backToCart()
        }
    }
}