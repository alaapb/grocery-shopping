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

class SignInFragment : Fragment() {
    private lateinit var back: Button
    private lateinit var onClickSignInListener: OnClickSignInListener

    interface OnClickSignInListener {

    }

    companion object {
        fun newInstance() = SignInFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnClickSignInListener) {
            onClickSignInListener = context
        }else{
            throw RuntimeException("Check CallBack something might be wrong")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signin_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}