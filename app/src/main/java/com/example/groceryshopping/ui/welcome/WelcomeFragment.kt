package com.example.groceryshopping.ui.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.groceryshopping.R
import java.lang.RuntimeException

class WelcomeFragment : Fragment() {
    private lateinit var cont : Button
    private lateinit var onClickWelcomeListener: OnClickWelcomeListener
    private lateinit var signIn : Button

    interface OnClickWelcomeListener {
        fun goToSignIn();
        fun goToHome();
    }

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnClickWelcomeListener) {
            onClickWelcomeListener = context
        }else{
            throw RuntimeException("implement onclick com.example.groceryshopping.ui.welcome listener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.welcome_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cont = view.findViewById(R.id.continueButton)
        signIn = view.findViewById(R.id.signinButton)

        signIn.setOnClickListener{
            onClickWelcomeListener.goToSignIn()
        }

        cont.setOnClickListener {
            onClickWelcomeListener.goToHome()
        }
    }
}