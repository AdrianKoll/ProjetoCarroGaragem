package com.koll.meu_app_car.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.koll.meu_app_car.R

class WelcomeActivity : AppCompatActivity() {

    private lateinit var buttonSignIn: Button
    private lateinit var buttonSignUp: Button
    private lateinit var buttonFacebook: ImageButton
    private lateinit var buttonTwitter: ImageButton
    private lateinit var buttonLinkedIn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        buttonFacebook = findViewById(R.id.buttonFacebook)
        buttonTwitter = findViewById(R.id.buttonTwitter)
        buttonLinkedIn = findViewById(R.id.buttonLinkedIn)
    }

    private fun setupClickListeners() {
        buttonSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        buttonSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Placeholder para redes sociais
        buttonFacebook.setOnClickListener {
            // TODO: Implementar login com Facebook
        }

        buttonTwitter.setOnClickListener {
            // TODO: Implementar login com Twitter
        }

        buttonLinkedIn.setOnClickListener {
            // TODO: Implementar login com LinkedIn
        }
    }
}

