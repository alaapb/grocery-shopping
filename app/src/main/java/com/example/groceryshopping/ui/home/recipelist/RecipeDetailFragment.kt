package com.example.groceryshopping.ui.home.recipelist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.groceryshopping.databinding.RecipeDetailFragmentBinding
import com.example.groceryshopping.sources.database.cartrecipes.CartRecipe
import com.example.groceryshopping.ui.home.util.Util
import com.example.groceryshopping.ui.home.util.UtilInterface
import com.example.groceryshopping.viewmodel.CartViewModel
import com.example.groceryshopping.viewmodel.RecipeViewModel
import java.lang.RuntimeException

class RecipeDetailFragment : Fragment() {

    private lateinit var onClickDetailListener: OnClickDetailListener
    private lateinit var binding:RecipeDetailFragmentBinding
    private var util : UtilInterface = Util()

    interface OnClickDetailListener {
        fun goToHome();
    }

    companion object {
        fun newInstance() = RecipeDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnClickDetailListener) {
            onClickDetailListener = context
        }else{
            throw RuntimeException("must implement on click detail listener")
        }
    }

//    fun getIngredients(ingredients : List<ExtendedIngredient>) : String {
//        val listOfIngredients : ArrayList<String> = ArrayList<String>()
//        for (ingredient in ingredients) {
//            listOfIngredients.add(ingredient.name + "; " + ingredient.measures.us.amount.toString() + "; " + ingredient.measures.us.unitShort + "\n")
//        }
//        return listOfIngredients.toString().substring(1, listOfIngredients.toString().length - 1)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewmodel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)

        val cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        viewmodel.curRecipe.observe(viewLifecycleOwner, {
            binding.recipeTitle.text = it.title
            binding.recipeIngredients.text = util.replaceCommaSemiBrackets(util.getIngredients(it.extendedIngredients).toString())
            binding.instructionText.text = util.replaceCommaSemiBrackets(util.parseInstruction(it.analyzedInstructions).toString())

            binding.servingValue.text = it.servings.toString()
            util.setImageView(binding.recipeImage, it.image)
        //            binding.recipeIngredients.text = it.ingredients.toString().substring(1, it.ingredients.toString().length - 1)
        })

        binding.addCartButton.setOnClickListener {

            if(binding.quantity.text.toString() == "") {
            Toast.makeText(requireActivity(), "Please Enter A Serving Size", Toast.LENGTH_SHORT).show()
            }else{
                if(cartViewModel.ifRowExists(viewmodel.curRecipe.value!!.id)) {
                    cartViewModel.updateCartRecipe(binding.quantity.text.toString(), viewmodel.curRecipe.value!!.id)
                }else {
                    cartViewModel.addCartRecipe(
                        CartRecipe(
                            viewmodel.curRecipe.value!!.id,
                            viewmodel.curRecipe.value!!.title,
                            util.getCartIngredients(
                                viewmodel.curRecipe.value!!.extendedIngredients,
                                binding.quantity.text.toString().toDouble(),
                                viewmodel.curRecipe.value!!.servings
                            ),
                            binding.quantity.text.toString(),
                            util.parseInstruction(viewmodel.curRecipe.value!!.analyzedInstructions),
                            viewmodel.curRecipe.value!!.image,
                            viewmodel.curRecipe.value!!.imageType,
                            viewmodel.curRecipe.value!!.servings
                        )
                    )
                }
                onClickDetailListener.goToHome()
            }
        }
    }
}