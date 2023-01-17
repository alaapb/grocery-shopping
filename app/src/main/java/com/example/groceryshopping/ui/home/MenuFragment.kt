package com.example.groceryshopping.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.groceryshopping.databinding.MenuOptionsFragmentBinding
import java.lang.RuntimeException

class MenuFragment : Fragment() {

    private lateinit var onClickMenuListener: OnClickMenuListener
    private lateinit var binding: MenuOptionsFragmentBinding

    companion object {
        fun newInstance() = MenuFragment()
    }

    interface OnClickMenuListener {
        fun goToCart();
        fun goToHome();
        fun goToCheckout();
        fun goToStores();
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is OnClickMenuListener) {
            onClickMenuListener = context
        }else{
            throw RuntimeException("make sure on click menu listener is implemented")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MenuOptionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cartButton.setOnClickListener {
            onClickMenuListener.goToCart()
        }

        binding.homeButton.setOnClickListener {
            onClickMenuListener.goToHome()
        }

        binding.checkoutButton.setOnClickListener {
            onClickMenuListener.goToCheckout()
        }

        binding.storeButton.setOnClickListener {
            onClickMenuListener.goToStores()
        }

    }

}