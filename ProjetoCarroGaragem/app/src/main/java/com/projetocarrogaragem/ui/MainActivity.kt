package com.projetocarrogaragem.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.projetocarrogaragem.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnSignIn = findViewById<Button>(R.id.buttonSignIn)
        val btnSignUp = findViewById<Button>(R.id.buttonSignUp)

        // Botão "Entrar" → vai para tela de login
        btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        // Botão "Cadastrar" → vai para tela de registro
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}