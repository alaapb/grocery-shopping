package com.example.groceryshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryshopping.ui.welcome.WelcomeFragment

class MainActivity : AppCompatActivity(),
    WelcomeFragment.OnClickWelcomeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WelcomeFragment.newInstance())
                .commitNow()
        }
    }

    override fun goToSignIn() {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, SignInFragment.newInstance())
//            .commitNow()
    }

    override fun goToHome() {

        startActivity(Intent(this, HomeActivity::class.java))
    }
}