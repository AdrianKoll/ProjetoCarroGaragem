package com.projetocarrogaragem.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projetocarrogaragem.R

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Botões principais
        val btnSignIn: Button = findViewById(R.id.buttonSignIn)
        val btnSignUp: Button = findViewById(R.id.buttonSignUp)

        // Botões sociais
        val btnFacebook: ImageButton = findViewById(R.id.buttonFacebook)
        val btnTwitter: ImageButton = findViewById(R.id.buttonTwitter)
        val btnLinkedIn: ImageButton = findViewById(R.id.buttonLinkedIn)

        // Navegação principal
        btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // Placeholders para redes sociais (futuras integrações)
        btnFacebook.setOnClickListener {
            Toast.makeText(this, "Login com Facebook em breve!", Toast.LENGTH_SHORT).show()
        }

        btnTwitter.setOnClickListener {
            Toast.makeText(this, "Login com Twitter em breve!", Toast.LENGTH_SHORT).show()
        }

        btnLinkedIn.setOnClickListener {
            Toast.makeText(this, "Login com LinkedIn em breve!", Toast.LENGTH_SHORT).show()
        }
    }
}